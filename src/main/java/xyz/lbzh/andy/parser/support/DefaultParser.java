package xyz.lbzh.andy.parser.support;

import xyz.lbzh.andy.core.Definition;
import xyz.lbzh.andy.expression.*;
import xyz.lbzh.andy.expression.ast.BracketExpression;
import xyz.lbzh.andy.expression.ast.CurlyBracketExpression;
import xyz.lbzh.andy.parser.Parser;
import xyz.lbzh.andy.tokenizer.Token;
import xyz.lbzh.andy.tokenizer.TokenFlag;
import xyz.lbzh.andy.tokenizer.Tokenizer;

import java.io.*;

public class DefaultParser implements Parser<Expression> {
    private Tokenizer<Token> tokenizer;

    private Token currentToken = Definition.HOF;

    public DefaultParser(Tokenizer<Token> tokenizer) {
        this.tokenizer = tokenizer;
    }

    @Override
    public Expression parseString(String expression) {
        tokenizer.init(new StringReader(expression));
        try {
            Expression rst = expression();
            tokenizer.close();
            return rst;
        } catch (Exception e) {
            e.printStackTrace();
            return ExpressionType.NIL;
        }
    }

    @Override
    public Expression parseFile(String fileName){
        CurlyBracketExpression curlyBracketExpression = ExpressionFactory.curlyBracket();
        try {
            tokenizer.init(new FileReader(fileName));
            while (hasNext()) {
                curlyBracketExpression.add(expression());
            }
            tokenizer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return curlyBracketExpression;
    }

    @Override
    public boolean hasNext() {
        return tokenizer.hasNext();
    }


    @Override
    public void close() throws IOException {
        tokenizer.close();
    }

    @Override
    public Expression next(){
        try {
            return expression();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
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
     * Generate an expressione
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
            return combine(ExpressionFactory.roundBracket(TokenFlag.POINT, left, combinator()));
        } else if (getToken() == TokenFlag.COLON) { //e.g. left: ...
            nextToken();
//            return ExpressionFactory.roundBracket(NameExpression.PAIR, left, expression());
            return ExpressionFactory.pair(left, expression());
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
        } else if (!hasNext()) { //文件结尾
            return Definition.EOF;
        } else { //其他字符跳过
            nextToken(); //eat
            return combinator();
        }

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
        while (getToken() != TokenFlag.CURLY_BRACKET_RIGHT) {
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
        while (getToken() != TokenFlag.SQUARE_BRACKET_RIGHT) {
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
            return roundBracket(commaExpression(ExpressionFactory.squareBracket(left)));
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

    /**
     * e.g. (exp1 exp2 ...)
     * @return
     */
    private BracketExpression parseRoundBracket(BracketExpression sexpression) throws Exception {
        while (getToken() != TokenFlag.ROUND_BRACKET_RIGHT) {
            sexpression.add(expression());
        }
        return sexpression;
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

}
