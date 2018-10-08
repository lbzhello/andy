package xyz.lbzh.andy.parser.support;

import xyz.lbzh.andy.core.Definition;
import xyz.lbzh.andy.expression.Expression;
import xyz.lbzh.andy.expression.ExpressionContext;
import xyz.lbzh.andy.expression.ExpressionFactory;
import xyz.lbzh.andy.expression.RoundBracketed;
import xyz.lbzh.andy.expression.support.*;
import xyz.lbzh.andy.parser.Parser;
import xyz.lbzh.andy.tokenizer.Tokenizer;

import java.io.*;
import java.util.Objects;

public class DefaultParser implements Parser<Expression> {
    private Tokenizer<Expression> tokenizer;

    private Expression currentToken = Definition.HOF;

    public DefaultParser(Tokenizer<Expression> tokenizer) {
        this.tokenizer = tokenizer;
    }

    @Override
    public Expression parse(String fileName){
        CurlyBracketExpression curlyBracketExpression = new CurlyBracketExpression();
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
    public Expression next(){
        try {
            return expression();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private Expression nextToken() {
        currentToken = tokenizer.next();
        return currentToken;
    }

    private Expression getToken() {
        return currentToken;
    }

    private Expression expression() throws Exception {
        Expression expression = combine(combinator());
        if (Definition.isBinary(getToken().toString())) { //e.g. expression op ...
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
        if (Definition.isBinary(getToken().toString())) { //e.g. left op right op2 ...
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
        if (Objects.equals(getToken().toString(), "(")) { //e.g. left(...)...
            BracketExpression bracketExpression = ExpressionFactory.roundBracket(left);
            bracketExpression.list().addAll(this.roundBracketExpression().list());
            return combine(bracketExpression);
        } else if (Objects.equals(getToken().toString(), "{")) { //e.g. left{...}...
//            return combine(ExpressionFactory.roundBracket(NameExpression.DEFINE, left, curlyBracketExpression()));
            return combine(ExpressionFactory.define(left, curlyBracketExpression()));
        } else if (Objects.equals(getToken().toString(), ":")) { //e.g. left: ...
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
        if(getToken() instanceof SymbolExpression){ //e.g. name...
            Expression expression = getToken();
            nextToken(); //eat "expression"
            //if it's unary operator
            if (Definition.isUnary(expression.toString())) {
                return unaryExpression(expression);
            }
            return expression;
        } else if (getToken() instanceof StringExpression) { //e.g. "name"...
            Expression expression = getToken();
            nextToken(); //eat
            return expression;
        } else if (getToken() instanceof NumberExpression) { //e.g. 123...
            Expression expression = getToken();
            nextToken(); //eat
            return expression;
        } else if (Objects.equals(getToken().toString(), "(") || Objects.equals(getToken().toString(), Definition.SPACE + "(")) { //e.g. (...)...
            return roundBracketExpression();
        } else if (Objects.equals(getToken().toString(), "[") || Objects.equals(getToken().toString(), Definition.SPACE + "[")) { //e.g. [...]...
            return squareBracketExpression();
        } else if (Objects.equals(getToken().toString(), "{") || Objects.equals(getToken().toString(), Definition.SPACE + "{")) { //e.g. {...}...
            return curlyBracketExpression();
        } else if (getToken() == Definition.EOF){ //文件结尾
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
        while (!Objects.equals(getToken().toString(), "}")) {
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
        while (!Objects.equals(getToken().toString(), "]")) {
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
        if (Objects.equals(getToken().toString(), ")")) { //e.g. ()
            nextToken(); //eat ")"
            return ExpressionFactory.roundBracket();
        }

        Expression expression = expression();
        if (Objects.equals(getToken().toString(), ")")) { //e.g. (expression)
            nextToken(); //eat ")"
            if (expression.getClass().getDeclaredAnnotation(RoundBracketed.class) == null) {
                return ExpressionFactory.roundBracket(expression);
            } else {
                return expression.shift(BracketExpression.class);

            }
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
        if (Objects.equals(getToken().toString(), ",")) { //e.g. left, ...
            return roundBracket(commaExpression(ExpressionFactory.squareBracket(left)));
        } else if (Objects.equals(getToken().toString(), ";")) { //e.g. left; ...
            return roundBracket(semicolonExpression(ExpressionFactory.squareBracket(left)));
        } else if (!Objects.equals(getToken().toString(), ")")){ //e.g. left ritht
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
        while (Objects.equals(getToken().toString(), ";")) {
            nextToken(); //eat ";"
            Expression expression = expression();
            if (Objects.equals(getToken().toString(), ",")) { //e.g. (expr1, expr2; expr3, expr4 ...
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
        while (!Objects.equals(getToken().toString(), ")")) {
            sexpression.add(expression());
        }
        return sexpression;
    }

    /**
     * e.g. (expr1, expr2, ...)
     * @return
     */
    private BracketExpression commaExpression(BracketExpression squareBracketExpression) throws Exception {
        while (Objects.equals(getToken().toString(), ",")) {
            nextToken(); //eat ","
            squareBracketExpression.add(expression());
        }
//        if (!Objects.equals(getToken().toString(), ";")
//                || !Objects.equals(getToken().toString(), ")")) throw new Exception("Syntax Error!");
        return squareBracketExpression;
    }


    @Override
    public boolean hasNext() {
        return getToken() != Definition.EOF;
    }


    @Override
    public void close() throws IOException {
        tokenizer.close();
    }
}
