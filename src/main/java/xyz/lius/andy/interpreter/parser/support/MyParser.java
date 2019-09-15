package xyz.lius.andy.interpreter.parser.support;

import xyz.lius.andy.interpreter.parser.Parser;
import xyz.lius.andy.interpreter.tokenizer.LineNumberToken;
import xyz.lius.andy.interpreter.tokenizer.Token;
import xyz.lius.andy.core.Definition;
import xyz.lius.andy.expression.Expression;
import xyz.lius.andy.expression.ExpressionFactory;
import xyz.lius.andy.expression.Operator;
import xyz.lius.andy.expression.ast.BracketExpression;
import xyz.lius.andy.expression.ast.CurlyBracketExpression;
import xyz.lius.andy.expression.ast.ErrorExpression;
import xyz.lius.andy.expression.template.LineExpression;
import xyz.lius.andy.expression.template.TemplateExpression;
import xyz.lius.andy.expression.template.XmlExpression;
import xyz.lius.andy.expression.template.XmlTagExpression;
import xyz.lius.andy.io.CharIterator;
import xyz.lius.andy.io.support.FileCharIterator;
import xyz.lius.andy.io.support.StringCharIterator;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MyParser implements Parser<Expression> {
    private CharIterator iterator;
    private Loader loader;

    private class Loader {
        private Expression current;

        public Loader(Expression current) {
            this.current = current;
        }

        public Expression get() {
            return current;
        }

        public Expression next() {
            Expression tmp = current;
            current = combine(combinator());
            return tmp;
        }

        public boolean hasNext() {
            return current != Definition.EOF;
        }
    }

    @Override
    public Expression parseString(String expression) {
        iterator = new StringCharIterator(expression);
        try {
            return expression();
        } catch (Exception e) {
            e.printStackTrace();
            return Definition.NIL;
        }
    }

    @Override
    public Expression parseFile(String fileName) {
        CurlyBracketExpression curlyBracketExpression = ExpressionFactory.curlyBracket();
        try {
            iterator = new FileCharIterator(fileName);
            if (iterator.hasNext()) {
                loader = new Loader(combine(combinator()));
                while (loader.hasNext()) {
                    curlyBracketExpression.add(expression());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return curlyBracketExpression;
    }

    private Expression expression() {
        Expression expression = loader.next();
        if (Definition.isBinary(loader.get())) { //e.g. expression op ...
            return operator(expression, loader.get());
        }
        return expression;
    }

    /**
     * 将表达式用运算符组合起来
     * e.g. combine() || combine() + combine() || combine() = combine()*combine() || ...
     *
     * @return
     */
    private Expression operator(Expression left, Expression op) {
        loader.next(); //eat op
        Operator binary = Definition.getOperator(op);
        Expression right = loader.next();
        if (Definition.isBinary(loader.get())) { //e.g. left op right get ...
            Expression op2 = loader.get();
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
     * Generate an expression
     * e.g. combinator() || name(...) || name(...)(...) || name(...){...} || (...){...}(...)(...) || var a ||...
     *
     * @return
     */
    private Expression combine(Expression left) {
        try {

            if (iterator.current() == '(') { //e.g. left(...)...
                BracketExpression bracketExpression = ExpressionFactory.roundBracket(left);
                bracketExpression.add(roundBracketExpression().toArray());
                return combine(bracketExpression);
            } else if (iterator.current() == '{') { //e.g. left{...}...
                return combine(ExpressionFactory.define(left, curlyBracketExpression()));
            } else if (nextNotEmpty() == '.') { //e.g. left.right...
                iterator.next();
                return combine(ExpressionFactory.point(left, combinator()));
            } else if (iterator.current() == ':') { //e.g. left: ...
                iterator.next();
                return ExpressionFactory.colon(left, expression());
            } else {
                return left;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ErrorExpression("COMBINE");
        }

    }


    /**
     * 生成一个表达式组合子
     * e.g. (...) || {...} || [...] || "string" || 123 || symbol
     *
     * @return
     */
    private Expression combinator() {
        try {
            if (iterator.current() == '"') { //"some text"
                return nextString();
            } else if (iterator.current() == '(') { //e.g. (...)...
                Expression expression = roundBracketExpression();
                return expression;
            } else if (iterator.current() == '[') { //e.g. [...]...
                return squareBracketExpression();
            } else if (iterator.current() == '{') { //e.g. {...}...
                return curlyBracketExpression();
            } else if (iterator.current() == '`') { //tmpl
                iterator.next(); //eat start '`'
                Expression template = templateExpression();
                iterator.next(); //eat end '`'
                return template;
            } else if (iterator.current() == '<') { //xml
                iterator.next(); //notice
                Expression xml = xmlExpression();
                iterator.next(); //continue
                return xml;
            } else if (Character.isDigit(iterator.current())) { //number
                return nextNumber();
            } else if (iterator.current() == ',' || iterator.current() == ';' || Character.isWhitespace(iterator.current())) { //略过
                iterator.next(); //eat
                nextNotEmpty();
                return combinator();
            } else if (iterator.hasNext()) { //identifier
                Expression expression = nextSymbol();
                //if it's unary operator
                if (Definition.isUnary(expression)) {
                    loader.next();
                    return unaryExpression(expression);
                }
                return expression;
            } else {
                return Definition.EOF;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return new ErrorExpression("combinator");
        }

    }

    private int getLineNumber() {
        return 0; //1 based
    }

    private static final Map<Character, Token> delimiter = new HashMap<>();

    static {
        delimiter.put(',', Token.COMMA);
        delimiter.put(';', Token.SEMICOLON);
        delimiter.put('.', Token.POINT);
        delimiter.put(':', Token.COLON);
        delimiter.put('(', Token.ROUND_BRACKET_LEFT);
        delimiter.put(')', Token.ROUND_BRACKET_RIGHT);
        delimiter.put('{', Token.CURLY_BRACKET_LEFT);
        delimiter.put('}', Token.CURLY_BRACKET_RIGHT);
        delimiter.put('[', Token.SQUARE_BRACKET_LEFT);
        delimiter.put(']', Token.SQUARE_BRACKET_RIGHT);
        delimiter.put('\\', Token.SLASH_LEFT);
        delimiter.put('"', Token.QUOTE_MARK_DOUBLE);
        delimiter.put('\'', Token.QUOTE_MARK_SINGLE);
    }

    private static final boolean isDelimiter(Character c) {
        return delimiter.containsKey(c);
    }

    private char nextNotEmpty() {
        while (Character.isWhitespace(iterator.current())) {
            iterator.next();
        }
        return iterator.current();
    }

    /**
     * e.g. "some string"
     *
     * @return
     * @throws IOException
     */
    private LineNumberToken nextString() {
        StringBuffer sb = new StringBuffer();
        iterator.next(); //eat '"'
        while (iterator.current() != '"') {
            sb.append(iterator.current());
            iterator.next();
        }
        iterator.next(); //eat '"'
        return ExpressionFactory.string(sb.toString(), getLineNumber());
    }

    /**
     * e.g. 1234 12.23
     *
     * @return
     * @throws IOException
     */
    private LineNumberToken nextNumber() {
        StringBuffer sb = new StringBuffer();
        while (iterator.current() == '.' || !Character.isWhitespace(iterator.current()) && !isDelimiter(iterator.current()) && iterator.current() != '\uFFFF') {
            sb.append(iterator.current());
            iterator.next();
        }
        return ExpressionFactory.number(sb.toString(), getLineNumber());
    }

    /**
     * e.g. name2, na-rt, sym+nnn...
     *
     * @return
     * @throws IOException
     */
    private Token nextSymbol() {
        StringBuffer sb = new StringBuffer();
        while (!Character.isWhitespace(iterator.current()) && !isDelimiter(iterator.current()) && iterator.current() != '\uFFFF') {
            sb.append(iterator.current());
            iterator.next();
        }
        return ExpressionFactory.symbol(sb.toString(), getLineNumber());
    }

    private Token nextTmpl() {
        StringBuilder segment = new StringBuilder();
        while (iterator.hasNext()) {
            switch (iterator.current()) {
                //定界符, '\n' 和 '|' 之间的空格忽略
                case '\n':
                    segment.append(iterator.current());
                    iterator.next(); //eat '\n'
//                    lineNumber++;
                    int start = segment.length();
                    while (Character.isWhitespace(iterator.current())) {
                        segment.append(iterator.current());
                        iterator.next();
                    }
                    if (iterator.current() == '|') {
                        segment.delete(start, segment.length());
                        iterator.next(); //eat '|'
                    }
                    break;
                case '(':
                    return ExpressionFactory.string(segment.toString(), getLineNumber());
                case '\\':
                    iterator.next(); //eat
                    if (Character.isWhitespace(iterator.current())) {
                        iterator.next(); //skip
                    } else {
                        segment.append(iterator.current()); //add to buffer
                        iterator.next();
                    }
                    break;
                case '`':
                    return ExpressionFactory.string(segment.toString(), getLineNumber());
                default:
                    segment.append(iterator.current());
            }
        }
        throw new RuntimeException("Lack of '`' to end the template!");
    }

    public Token nextXml() {
        StringBuilder segment = new StringBuilder();
        while (iterator.hasNext()) {
            if (iterator.current() == '(' || iterator.current() == '/'
                    || iterator.current() == '<' || iterator.current() == '>') {
                return ExpressionFactory.string(segment.toString(), getLineNumber());
            } else if (iterator.current() == '\n') {
                iterator.next();
//                lineNumber++;
            } else {
                segment.append(iterator.current());
            }
        }

        throw new RuntimeException("Lack of '`' to end the template!");
    }

    /**
     * match <expe / expr>
     *
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
     * str = "world"
     * `
     * hello (str)!
     * `
     *
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
//        tokenizer.reset();
        while (iterator.current() != ')' && iterator.hasNext()) {
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
     *
     * @return
     */
    private CurlyBracketExpression curlyBracketExpression() throws Exception {
        iterator.next(); //eat '{'
        CurlyBracketExpression curlyBracketExpression = ExpressionFactory.curlyBracket();
        while (iterator.current() != '}' && iterator.hasNext()) {
            curlyBracketExpression.add(expression());
        }
        iterator.next(); //eat '}'
        return curlyBracketExpression;
    }


    /**
     * e.g. [...]
     *
     * @return
     */
    private BracketExpression squareBracketExpression() throws Exception {
        iterator.next(); //eat '['
        BracketExpression squareBracketExpression = ExpressionFactory.squareBracket();
        while (iterator.current() != ']' && iterator.hasNext()) {
            squareBracketExpression.add(expression());
        }

        iterator.next(); //eat ']'
        return squareBracketExpression;
    }

    /**
     * e.g. (...)
     *
     * @return
     */
    private BracketExpression roundBracketExpression() throws Exception {
        iterator.next(); //eat '('
        if (nextNotEmpty() == ')') { //e.g. ()
            iterator.next();
            return ExpressionFactory.roundBracket();
        }

        loader.next();
        Expression expression = expression();
        if (iterator.current() == ')') { //e.g. (expression)
            iterator.next(); //eat ")"
            return ExpressionFactory.roundBracket(expression);
        } else {
            return roundBracket(expression);
        }

    }

    /**
     * e.g. expression() expression() || expression(), expression() || expression();expression() || expression(),expression(); expression(), ...
     *
     * @param left
     * @return
     */
    private BracketExpression roundBracket(Expression left) throws Exception {
        if (iterator.current() == ',') { //e.g. left, ...
            return roundBracket(commaExpression(ExpressionFactory.comma(left)));
        } else if (iterator.current() == ';') { //e.g. left; ...
            return roundBracket(semicolonExpression(ExpressionFactory.squareBracket(left)));
        } else if (iterator.current() != ')') { //e.g. left ritht
            return roundBracket(parseRoundBracket(ExpressionFactory.roundBracket(left)));
        } else {
            iterator.next(); //eat ")"
            return (BracketExpression) left;
        }
    }

    /**
     * e.g. (exp1 exp2 ...)
     *
     * @return
     */
    private BracketExpression parseRoundBracket(BracketExpression roundBracket) throws Exception {
        while (iterator.current() != ')' && iterator.hasNext()) {
            roundBracket.add(expression());
        }
        return roundBracket;
    }

    /**
     * e.g. (expr1, expr2, ...)
     *
     * @return
     */
    private BracketExpression commaExpression(BracketExpression squareBracketExpression) throws Exception {
        while (iterator.current() == ',') {
            iterator.next(); //eat ","
            squareBracketExpression.add(expression());
        }
//        if (!Objects.equals(tokenizer.current().toString(), ";")
//                || !Objects.equals(tokenizer.current().toString(), ")")) throw new Exception("Syntax Error!");
        return squareBracketExpression;
    }

    /**
     * e.g. (expr1,expr2, ...; expr3, expr4; ...) => [[expr1 expr2 ...] [expr3 expr4] [...]]
     *
     * @return
     */
    private BracketExpression semicolonExpression(BracketExpression squareBracketExpression) throws Exception {
        while (iterator.current() == ';') {
            iterator.next(); //eat ";"
            Expression expression = expression();
            if (iterator.current() == ',') { //e.g. (expr1, expr2; expr3, expr4 ...
                expression = commaExpression(ExpressionFactory.squareBracket(expression));
            }
            squareBracketExpression.add(expression);
        }
        return squareBracketExpression;
    }

}
