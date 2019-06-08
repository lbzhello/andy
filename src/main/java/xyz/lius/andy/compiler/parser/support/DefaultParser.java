package xyz.lius.andy.compiler.parser.support;

import xyz.lius.andy.core.Definition;
import xyz.lius.andy.expression.*;
import xyz.lius.andy.expression.ast.*;
import xyz.lius.andy.expression.template.XmlTagExpression;
import xyz.lius.andy.expression.template.LineExpression;
import xyz.lius.andy.expression.template.TemplateExpression;
import xyz.lius.andy.expression.template.XmlExpression;
import xyz.lius.andy.compiler.parser.Parser;
import xyz.lius.andy.io.CharIterator;
import xyz.lius.andy.io.support.FileCharIterator;
import xyz.lius.andy.io.support.StringCharIterator;
import xyz.lius.andy.compiler.tokenizer.Token;
import xyz.lius.andy.compiler.tokenizer.TokenFlag;
import xyz.lius.andy.compiler.tokenizer.Tokenizer;

public class DefaultParser implements Parser<Expression> {
    private CharIterator iterator;
    private Tokenizer<Token> tokenizer;

    private Token currentToken = Definition.HOF;

    public DefaultParser(Tokenizer<Token> tokenizer) {
        this.tokenizer = tokenizer;
    }

    @Override
    public Expression parseString(String expression) {
        iterator = new StringCharIterator(expression);
        tokenizer.setResource(iterator);
        try {
            return expression();
        } catch (Exception e) {
            e.printStackTrace();
            return ExpressionType.NIL;
        }
    }

    @Override
    public Expression parseFile(String fileName){
        CurlyBracketExpression curlyBracketExpression = ExpressionFactory.curlyBracket();
        try {
            iterator = new FileCharIterator(fileName);
            tokenizer.setResource(iterator);
            while (iterator.hasNext()) {
                curlyBracketExpression.add(expression());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return curlyBracketExpression;
    }

    private Token nextToken() {
        currentToken = tokenizer.next();
        return currentToken;
    }

    private Token getToken() {
        return currentToken;
    }

    private Expression expression() throws Exception {
        Expression expression = combine(combinator());
        if (Definition.isBinary(getToken())) { //e.g. expression op ...
            expression = operator(expression, getToken());
        }
        return expression;
    }

    /**
     * 将表达式用运算符组合起来
     * e.g. combine() || combine() + combine() || combine() = combine()*combine() || ...
     * @return
     */
    private Expression operator(Expression left, Expression op) throws Exception {
        nextToken(); //eat op
        Expression right = combine(combinator());
        if (Definition.isBinary(getToken())) { //e.g. left op right op2 ...
            Expression op2 = getToken();
            if (Definition.comparePriority(op.toString(), op2.toString()) < 0) { //e.g. left op (right op2 ...)
                return ExpressionFactory.roundBracket(op, left, operator(right, op2));
            } else { //e.g. (left op right) op2 ...
                return operator(ExpressionFactory.roundBracket(op, left, right),op2);
            }
        } else { //e.g. left op right
            return ExpressionFactory.roundBracket(op, left, right);
        }
    }

    /**
     * Generate an expression
     * e.g. combinator() || name(...) || name(...)(...) || name(...){...} || (...){...}(...)(...) || var a ||...
     * @return
     */
    private Expression combine(Expression left) throws Exception {
        if (getToken() == TokenFlag.ROUND_BRACKET_LEFT) { //e.g. left(...)...
            BracketExpression bracketExpression = ExpressionFactory.roundBracket(left);
            bracketExpression.list().addAll(this.roundBracketExpression().list());
            return combine(bracketExpression);
        } else if (getToken() == TokenFlag.CURLY_BRACKET_LEFT) { //e.g. left{...}...
            return combine(ExpressionFactory.define(left, curlyBracketExpression()));
        } else if (getToken() == TokenFlag.POINT) { //e.g. left.right...
            return combine(ExpressionFactory.point(left, combinator()));
        } else if (getToken() == TokenFlag.COLON) { //e.g. left: ...
            nextToken();
            return ExpressionFactory.colon(left, expression());
        } else {
            return left;
        }

    }

    /**
     * 生成一个表达式组合子
     * e.g. (...) || {...} || [...] || "string" || 123 || symbol
     * @return
     */
    private Expression combinator() throws Exception {
        if (ExpressionUtils.isSymbol(getToken())) { //e.g. name...
            Expression expression = getToken();
            nextToken(); //eat "expression"
            //if it's unary operator
            if (Definition.isUnary(expression)) {
                return unaryExpression(expression);
            }
            return expression;
        } else if (ExpressionUtils.isString(getToken())) { //e.g. "name"...
            Expression expression = getToken();
            nextToken(); //eat
            return expression;
        } else if (ExpressionUtils.isNumber(getToken())) { //e.g. 123...
            Expression expression = getToken();
            nextToken(); //eat
            return expression;
        } else if (getToken() == TokenFlag.ROUND_BRACKET_LEFT || getToken() == TokenFlag.ROUND_BRACKET_FREE) { //e.g. (...)...
            return roundBracketExpression();
        } else if (getToken() == TokenFlag.SQUARE_BRACKET_LEFT || getToken() == TokenFlag.SQUARE_BRACKET_FREE) { //e.g. [...]...
            return squareBracketExpression();
        } else if (getToken() == TokenFlag.CURLY_BRACKET_LEFT || getToken() == TokenFlag.CURLY_BRACKET_FREE) { //e.g. {...}...
            return curlyBracketExpression();
        } else if (getToken() == TokenFlag.BACK_QUOTE) { //e.g. ``
            Expression template = templateExpression();
            nextToken(); //continue parse
            return template;
        } else if (getToken() == TokenFlag.ANGLE_BRACKET) {
            Expression xml = xmlExpression();
            nextToken(); //continue
            return xml;
        } else if (!iterator.hasNext()) { //文件结尾
            return Definition.EOF;
        } else { //其他字符跳过
            nextToken(); //eat
            return combinator();
        }

    }

    /**
     * match <expe / expr>
     * @return
     * @throws Exception
     */
    private Expression xmlExpression() throws Exception {
        XmlExpression xml = ExpressionFactory.xml();
        XmlTagExpression angleBracket = parseXmlLable();
        if (angleBracket.isClose()) {
            xml.setCloseTag(angleBracket);
        } else {
            xml.setStartTag(angleBracket);
            xml.setBody(parseXmlBody());
            xml.setCloseTag(parseXmlLable());
        }
        return xml;
    }

    private XmlTagExpression parseXmlLable() throws Exception {
        XmlTagExpression angleBracket = ExpressionFactory.xmlTag();
        while (iterator.current() != '>') {
            if (iterator.current() == '(') {
                angleBracket.add(insertRoundBracket());
            } else if (iterator.current() == '/') {
                angleBracket.setClose(true);
                angleBracket.add(ExpressionFactory.string("/"));
                iterator.next();
            } else {
                StringBuffer sb = new StringBuffer();
                while (iterator.current() != '(' && iterator.current() != '>' && iterator.current() != '/') {
                    sb.append(iterator.current());
                    iterator.next();
                }
                angleBracket.add(ExpressionFactory.string(sb.toString()));
            }
        }
        angleBracket.add(ExpressionFactory.string(">"));
        iterator.next(); //eat '>'
        return angleBracket;
    }

    //
    private BracketExpression parseXmlBody() throws Exception {
        BracketExpression bracketExpression = ExpressionFactory.bracket();
        while (iterator.hasNext()) {
            if (iterator.current() == '<') {
                iterator.next();
                if (iterator.current() == '/') { //e.g. </...
                    iterator.previous(); //back to '<'
                    return bracketExpression;
                } else {
                    iterator.previous(); //back to '<'
                    bracketExpression.add(xmlExpression());
                }
            } else if (iterator.current() == '(') {
                bracketExpression.add(insertRoundBracket());
            } else {
                StringBuffer sb = new StringBuffer();
                while (iterator.current() != '(' && iterator.current() != '<' && iterator.current() != '>') {
                    sb.append(iterator.current());
                    iterator.next();
                }
                bracketExpression.add(ExpressionFactory.string(sb.toString().trim()));
            }
        }
        throw new Exception("Lack close tag in xml: " + bracketExpression);
    }

    /**
     * e.g.
     *     str = "world"
     *     `
     *         hello (str)!
     *     `
     * @return
     * @throws Exception
     */
    private Expression templateExpression() throws Exception {
        iterator.next(); //eat '`'
        TemplateExpression template = ExpressionFactory.template();
        while (iterator.hasNext()) {
            if (iterator.current() == '`') {
                iterator.next();
                return template;
            } else {
                template.addLine(lineExpression());
            }
        }

        return ExpressionFactory.error(template, "Missing closing tag '`'");
    }

    //parse one line
    private Expression lineExpression() throws Exception {
        LineExpression line = ExpressionFactory.line();
        while (iterator.hasNext()) {
            switch (iterator.current()) {
                case '\n':
                    line.add(ExpressionFactory.string("\n"));
                    iterator.next(); //eat '\n'
                    return line;
                case '`':
                    return line;
                case '(':
                    line.add(insertRoundBracket());
                    break;
                case '|':
                    iterator.next();
                    break;
                default:
                    line.add(stringExpression());
            }
        }
        return ExpressionFactory.error("Missing closing symbol when parse template");
    }

    //解析模板中得string片段, '|' 为定界符
    private Expression stringExpression() {
        StringBuffer sb = new StringBuffer();
        while (iterator.hasNext()) {
            switch (iterator.current()) {
                case '\n':
                    return ExpressionFactory.string(sb.toString());
                case '(':
                    return ExpressionFactory.string(sb.toString());
                case '`':
                    return ExpressionFactory.string(sb.toString());
                case '|': //定界符
                    sb.delete(0, sb.length());
                    iterator.next(); //eat
                    break;
                case '\\':
                    iterator.next(); //eat '\'
                    escapedChar(sb);
                    break;
                default:
                    sb.append(iterator.current());
                    iterator.next();
            }
        }
        return ExpressionFactory.error("Missing closing symbol when parse template");
    }

    //转意字符
    private void escapedChar(StringBuffer sb) {
        if (Character.isWhitespace(iterator.current())) { //skip whitespace
            iterator.next();
        } else {
            sb.append(iterator.current());
            iterator.next(); //skip
        }
    }

    //在模板中嵌入(...)
    private Expression insertRoundBracket() throws Exception {
        iterator.next(); //eat '('
        BracketExpression roundBracket = ExpressionFactory.roundBracket();
        this.currentToken = Definition.HOF;
        while (getToken() != TokenFlag.ROUND_BRACKET_RIGHT && iterator.hasNext()) {
            roundBracket.add(expression());
        }
        return roundBracket;
    }

    private BracketExpression unaryExpression(Expression op) throws Exception {
        int size = Definition.getNumberOfOperands(op.toString());
        BracketExpression roundBracketExpression = ExpressionFactory.roundBracket(op);
        for (int i = 0; i < size; i++) {
            roundBracketExpression.add(expression());
        }
        return roundBracketExpression;
    }

    /**
     * e.g. {...}
     * @return
     */
    private CurlyBracketExpression curlyBracketExpression() throws Exception {
        nextToken(); //eat '{'
        CurlyBracketExpression curlyBracketExpression = ExpressionFactory.curlyBracket();
        while (getToken() != TokenFlag.CURLY_BRACKET_RIGHT && iterator.hasNext()) {
            curlyBracketExpression.add(expression());
        }
        nextToken(); //eat '}'
        return curlyBracketExpression;
    }


    /**
     * e.g. [...]
     * @return
     */
    private BracketExpression squareBracketExpression() throws Exception {
        nextToken(); //eat '['
        BracketExpression squareBracketExpression = ExpressionFactory.squareBracket();
        while (getToken() != TokenFlag.SQUARE_BRACKET_RIGHT && iterator.hasNext()) {
            squareBracketExpression.add(expression());
        }

        nextToken(); //eat ']'
        return squareBracketExpression;
    }

    /**
     * e.g. (...)
     * @return
     */
    private BracketExpression roundBracketExpression() throws Exception {
        nextToken(); //eat '('
        if (getToken() == TokenFlag.ROUND_BRACKET_RIGHT) { //e.g. ()
            nextToken(); //eat ")"
            return ExpressionFactory.roundBracket();
        }

        Expression expression = expression();
        if (getToken() == TokenFlag.ROUND_BRACKET_RIGHT) { //e.g. (expression)
            nextToken(); //eat ")"
            return ExpressionFactory.roundBracket(expression);
        } else {
            return roundBracket(expression);
        }

    }

    /**
     * e.g. expression() expression() || expression(), expression() || expression();expression() || expression(),expression(); expression(), ...
     * @param left
     * @return
     */
    private BracketExpression roundBracket(Expression left) throws Exception {
        if (getToken() == TokenFlag.COMMA) { //e.g. left, ...
            return roundBracket(commaExpression(ExpressionFactory.comma(left)));
        } else if (getToken() == TokenFlag.SEMICOLON) { //e.g. left; ...
            return roundBracket(semicolonExpression(ExpressionFactory.squareBracket(left)));
        } else if (getToken() != TokenFlag.ROUND_BRACKET_RIGHT){ //e.g. left ritht
            return roundBracket(parseRoundBracket(ExpressionFactory.roundBracket(left)));
        } else {
            nextToken(); //eat ")"
            return (BracketExpression)left;
        }
    }

    /**
     * e.g. (exp1 exp2 ...)
     * @return
     */
    private BracketExpression parseRoundBracket(BracketExpression roundBracket) throws Exception {
        while (getToken() != TokenFlag.ROUND_BRACKET_RIGHT && iterator.hasNext()) {
            roundBracket.add(expression());
        }
        return roundBracket;
    }

    /**
     * e.g. (expr1, expr2, ...)
     * @return
     */
    private BracketExpression commaExpression(BracketExpression squareBracketExpression) throws Exception {
        while (getToken() == TokenFlag.COMMA) {
            nextToken(); //eat ","
            squareBracketExpression.add(expression());
        }
//        if (!Objects.equals(getToken().toString(), ";")
//                || !Objects.equals(getToken().toString(), ")")) throw new Exception("Syntax Error!");
        return squareBracketExpression;
    }

    /**
     * e.g. (expr1,expr2, ...; expr3, expr4; ...) => [[expr1 expr2 ...] [expr3 expr4] [...]]
     * @return
     */
    private BracketExpression semicolonExpression(BracketExpression squareBracketExpression) throws Exception {
        while (getToken() == TokenFlag.SEMICOLON) {
            nextToken(); //eat ";"
            Expression expression = expression();
            if (getToken() == TokenFlag.COMMA) { //e.g. (expr1, expr2; expr3, expr4 ...
                expression = commaExpression(ExpressionFactory.squareBracket(expression));
            }
            squareBracketExpression.add(expression);
        }
        return squareBracketExpression;
    }

}
