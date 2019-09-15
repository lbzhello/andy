package xyz.lius.andy.interpreter.parser.support;

import xyz.lius.andy.core.Definition;
import xyz.lius.andy.expression.Expression;
import xyz.lius.andy.expression.ExpressionFactory;
import xyz.lius.andy.expression.Operator;
import xyz.lius.andy.expression.TypeCheck;
import xyz.lius.andy.expression.adapter.AddableExpressionAdapter;
import xyz.lius.andy.expression.adapter.BracketExpressionAdapter;
import xyz.lius.andy.expression.adapter.LambdaExpressionAdapter;
import xyz.lius.andy.expression.ast.BracketExpression;
import xyz.lius.andy.expression.ast.CommandExpression;
import xyz.lius.andy.expression.ast.CurlyBracketExpression;
import xyz.lius.andy.expression.ast.RoundBracketExpression;
import xyz.lius.andy.expression.operator.LambdaExpression;
import xyz.lius.andy.expression.operator.PointExpression;
import xyz.lius.andy.expression.template.LineExpression;
import xyz.lius.andy.expression.template.TemplateExpression;
import xyz.lius.andy.expression.template.XmlExpression;
import xyz.lius.andy.expression.template.XmlTagExpression;
import xyz.lius.andy.interpreter.parser.Parser;
import xyz.lius.andy.interpreter.tokenizer.Token;
import xyz.lius.andy.interpreter.tokenizer.Tokenizer;
import xyz.lius.andy.io.CharIterator;
import xyz.lius.andy.io.support.FileCharIterator;
import xyz.lius.andy.io.support.StringCharIterator;

public class DefaultParser implements Parser<Expression> {
    private CharIterator iterator;
    private Tokenizer<Token> tokenizer;

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
            return Definition.NIL;
        }
    }

    @Override
    public Expression parseFile(String fileName){
        CurlyBracketExpression curlyBracketExpression = ExpressionFactory.curlyBracket();
        try {
            iterator = new FileCharIterator(fileName);
            tokenizer.setResource(iterator);
            while (tokenizer.hasNext()) {
                curlyBracketExpression.add(expression());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return curlyBracketExpression;
    }

    private Expression expression() throws Exception {
        Expression expression = combinator();
        if (Definition.isBinary(tokenizer.current())) { //e.g. expression op ...
            expression = operator(expression, tokenizer.current());
        }

        while (isSeparator(tokenizer.current())) {
            tokenizer.next(); // eat
        }
        return expression;
    }

    /**
     * 将表达式用运算符组合起来
     * e.g. combine() || combine() + combine() || combine() = combine()*combine() || ...
     * @return
     */
    private Expression operator(Expression left, Expression op) throws Exception {
        tokenizer.next(); //eat op
        Operator binary = Definition.getOperator(op);
        Expression right = combinator();
        if (Definition.isBinary(tokenizer.current())) { //e.g. left op right op2 ...
            Expression op2 = tokenizer.current();
            if (Definition.compare(op, op2) < 0) { //e.g. left op (right op2 ...)
                binary.add(left);
                binary.add(operator(right, op2));
                return binary;
            } else { //e.g. (left op right) op2 ...
                binary.add(left);
                binary.add(right);
                return operator(binary, op2);
            }
        } else { //e.g. left op right
            binary.add(left);
            binary.add(right);
            return binary;
        }
    }

    /**
     * 表达式组合子
     * @return
     * @throws Exception
     */
    private Expression combinator() throws Exception {
        return combine(combinator0());
    }

    /**
     * 生成一个复合表达式组合子
     * e.g. combinator0() || name(...) || name(...)(...) || name(...){...} || (...){...}(...)(...) || let a ||...
     * @return
     */
    private Expression combine(Expression left) throws Exception {
        if (tokenizer.current() == Token.ROUND_BRACKET_LEFT //e.g. left(...)... 元素作为多个参数
                || tokenizer.current() == Token.CURLY_BRACKET_LEFT //e.g. left{...}... 元素作为一个参数
                || tokenizer.current() == Token.SQUARE_BRACKET_LEFT) { //e.g. left[2]... 元素作为一个参数
            return combine0(left);
        } else if (tokenizer.current() == Token.POINT) { //e.g. left.right...
            tokenizer.next();
            return combine(ExpressionFactory.point(left, combinator0()));
        } else {
            return left;
        }

    }

    private Expression combine0(Expression left) throws Exception {
        // 函数调用语法树
        // e.g.  f(...) || f{..} || f[...] || f(...)[...]{...}
        if (TypeCheck.isConstant(left) || left instanceof PointExpression) { // f(x) || boo.say()
            return doCombine(new BracketExpressionAdapter(ExpressionFactory.roundBracket(left)));
        } else {
            // lambda 语法树
            // e.g. (...){...} || [...]{...}
            LambdaExpression lambda = new LambdaExpression();
            if (TypeCheck.isRoundBracket(left)) {
                lambda.add(((RoundBracketExpression) left).toArray());
            } else {
                lambda.add(left);
            }
            return doCombine(new LambdaExpressionAdapter(lambda));
        }
    }

    private Expression doCombine(AddableExpressionAdapter left) throws Exception {
        if (tokenizer.current() == Token.ROUND_BRACKET_LEFT) { //e.g. left(...)... 元素作为多个参数
            left.add(roundBracketExpression().toArray());
            return doCombine(left);
        } else if (tokenizer.current() == Token.CURLY_BRACKET_LEFT) { //e.g. left{...}... 元素作为一个参数
            left.add(curlyBracketExpression());
            return doCombine(left);
        } else if (tokenizer.current() == Token.SQUARE_BRACKET_LEFT) { //e.g. left[2]... 元素作为一个参数
            left.add(squareBracketExpression());
            return doCombine(left);
        } else if (tokenizer.current() == Token.POINT) { //e.g. left.right...
            tokenizer.next();
            return combine(ExpressionFactory.point(left.getExpression(), combinator0()));
        } else {
            return left.getExpression();
        }

    }



    /**
     * 生成一个表达式组合子
     * e.g. (...) || {...} || [...] || "string" || 123 || symbol
     * @return
     */
    private Expression combinator0() throws Exception {
        if (TypeCheck.isSymbol(tokenizer.current())) { //e.g. name...
            Expression expression = tokenizer.current();
            tokenizer.next(); //eat "expression"
            //if it's unary operator
            if (Definition.isUnary(expression)) {
                return unaryExpression(expression);
            }
            return expression;
        } else if (TypeCheck.isString(tokenizer.current())) { //e.g. "name"...
            Expression expression = tokenizer.current();
            tokenizer.next(); //eat
            return expression;
        } else if (TypeCheck.isNumber(tokenizer.current())) { //e.g. 123...
            Expression expression = tokenizer.current();
            tokenizer.next(); //eat
            return expression;
        } else if (tokenizer.current() == Token.ROUND_BRACKET_LEFT || tokenizer.current() == Token.ROUND_BRACKET_LEFT_FREE) { //e.g. (...)...
            return roundBracketExpression();
        } else if (tokenizer.current() == Token.SQUARE_BRACKET_LEFT || tokenizer.current() == Token.SQUARE_BRACKET_LEFT_FREE) { //e.g. [...]...
            return squareBracketExpression();
        } else if (tokenizer.current() == Token.CURLY_BRACKET_LEFT || tokenizer.current() == Token.CURLY_BRACKET_LEFT_FREE) { //e.g. {...}...
            return curlyBracketExpression();
        } else if (tokenizer.current() == Token.BACK_QUOTE) { //e.g. ``
            tokenizer.next(); //eat start '`'
            Expression template = templateExpression();
            tokenizer.next(); //eat end '`'
            return template;
        } else if (tokenizer.current() == Token.ANGLE_BRACKET) {
            tokenizer.next(); //notice
            Expression xml = xmlExpression();
            tokenizer.next(); //continue
            return xml;
        } else if (!tokenizer.hasNext()) { //文件结尾
            return Definition.EOF;
        } else { //其他字符跳过
            tokenizer.next(); //eat
            return combinator();
        }

    }

    // 是否是间隔符
    private boolean isSeparator(Expression sep) {
        return sep == Token.EOL || sep == Token.SEMICOLON;
    }

    /**
     * 函数调用如果在同一行，可以省略括号
     * @return
     */
    private Expression commandExpression(Expression head) throws Exception {
        CommandExpression command = new CommandExpression(head);
        while (tokenizer.current() != Token.SEMICOLON ) {
            command.add(commandArg());
        }
        // 只有函数名字，返回函数本身，而不是 command
        // e.g.
        // def sum(x, y) x + y
        // sum 2 3 // command
        // sum     // self
        if (command.size() == 1) {
            return head;
        }
        return command;
    }

    //e.g. cmd arg1 arg2 ...
    private Expression commandArg() throws Exception {
        Expression expression = combinator();
        if (Definition.isBinary(tokenizer.current())) { //e.g. expression op ...
            expression = operator(expression, tokenizer.current());
        }

        return expression;
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
        TemplateExpression template = ExpressionFactory.template();
        while (iterator.hasNext()) {
            if (iterator.current() == '`') {
                iterator.next();
                return template;
            } else {
                template.add(lineExpression());
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

    //解析模板中string片段, '|' 为定界符
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
//        this.currentToken = Definition.HOF;
//        tokenizer.setResource(iterator);
        tokenizer.reset();
        while (tokenizer.current() != Token.ROUND_BRACKET_RIGHT && iterator.hasNext()) {
            roundBracket.add(expression());
        }
        return roundBracket;
    }

    private Expression unaryExpression(Expression op) throws Exception {
        int size = Definition.getOperands(op);
        Operator operator = Definition.getOperator(op);

        for (int i = 0; i < size; i++) {
            operator.add(expression());
        }
        return operator;
    }

    /**
     * e.g. {...}
     * @return
     */
    private CurlyBracketExpression curlyBracketExpression() throws Exception {
        tokenizer.next(); //eat '{'
        CurlyBracketExpression curlyBracketExpression = ExpressionFactory.curlyBracket();
         while (tokenizer.current() != Token.CURLY_BRACKET_RIGHT && iterator.hasNext()) {
            curlyBracketExpression.add(expression());
        }
        tokenizer.next(); //eat '}'
        return curlyBracketExpression;
    }


    /**
     * e.g. [...]
     * @return
     */
    private BracketExpression squareBracketExpression() throws Exception {
        tokenizer.next(); //eat '['
        BracketExpression squareBracketExpression = ExpressionFactory.squareBracket();
        while (tokenizer.current() != Token.SQUARE_BRACKET_RIGHT && iterator.hasNext()) {
            squareBracketExpression.add(expression());
        }

        tokenizer.next(); //eat ']'
        return squareBracketExpression;
    }

    /**
     * e.g. (...)
     * @return
     */
    private BracketExpression roundBracketExpression() throws Exception {
        tokenizer.next(); //eat '('
        if (tokenizer.current() == Token.ROUND_BRACKET_RIGHT) { //e.g. ()
            tokenizer.next(); //eat ")"
            return ExpressionFactory.roundBracket();
        }

        Expression expression = expression();
        if (tokenizer.current() == Token.ROUND_BRACKET_RIGHT) { //e.g. (expression)
            tokenizer.next(); //eat ")"
            if (expression instanceof CommandExpression) {
                return ExpressionFactory.roundBracket(((CommandExpression) expression).toArray());
            }
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
        if (tokenizer.current() == Token.COMMA) { //e.g. left, ...
            return roundBracket(commaExpression(ExpressionFactory.comma(left)));
        } else if (tokenizer.current() == Token.SEMICOLON) { //e.g. left; ...
            return roundBracket(semicolonExpression(ExpressionFactory.squareBracket(left)));
        } else if (tokenizer.current() != Token.ROUND_BRACKET_RIGHT){ //e.g. left ritht
            return roundBracket(parseRoundBracket(ExpressionFactory.roundBracket(left)));
        } else {
            tokenizer.next(); //eat ")"
            return (BracketExpression)left;
        }
    }

    /**
     * e.g. (exp1 exp2 ...)
     * @return
     */
    private BracketExpression parseRoundBracket(BracketExpression roundBracket) throws Exception {
        while (tokenizer.current() != Token.ROUND_BRACKET_RIGHT && iterator.hasNext()) {
            roundBracket.add(expression());
        }
        return roundBracket;
    }

    /**
     * e.g. (expr1, expr2, ...)
     * @return
     */
    private BracketExpression commaExpression(BracketExpression squareBracketExpression) throws Exception {
        while (tokenizer.current() == Token.COMMA) {
            tokenizer.next(); //eat ","
            squareBracketExpression.add(expression());
        }
//        if (!Objects.equals(tokenizer.current().toString(), ";")
//                || !Objects.equals(tokenizer.current().toString(), ")")) throw new Exception("Syntax Error!");
        return squareBracketExpression;
    }

    /**
     * e.g. (expr1,expr2, ...; expr3, expr4; ...) => [[expr1 expr2 ...] [expr3 expr4] [...]]
     * @return
     */
    private BracketExpression semicolonExpression(BracketExpression squareBracketExpression) throws Exception {
        while (tokenizer.current() == Token.SEMICOLON) {
            tokenizer.next(); //eat ";"
            Expression expression = expression();
            if (tokenizer.current() == Token.COMMA) { //e.g. (expr1, expr2; expr3, expr4 ...
                expression = commaExpression(ExpressionFactory.squareBracket(expression));
            }
            squareBracketExpression.add(expression);
        }
        return squareBracketExpression;
    }

}
