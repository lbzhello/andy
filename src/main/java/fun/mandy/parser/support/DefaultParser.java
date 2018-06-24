package fun.mandy.parser.support;

import fun.mandy.boot.Application;
import fun.mandy.constant.Constants;
import fun.mandy.constant.ExpressionTypeMode;
import fun.mandy.exception.Exceptions;
import fun.mandy.expression.Expression;
import fun.mandy.expression.Name;
import fun.mandy.expression.Pair;
import fun.mandy.expression.Unit;
import fun.mandy.expression.support.*;
import fun.mandy.parser.Parser;
import fun.mandy.tokenizer.Token;
import fun.mandy.tokenizer.Tokenizer;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.Reader;
import java.util.List;
import java.util.Objects;

@Named
public class DefaultParser implements Parser<Expression> {
    @Inject
    private Tokenizer<Token> tokenizer;

    private Token<Integer, String> currentToken = null;

    @Override
    public Expression next(){
        while (tokenizer.hasNext()) {
            try {
                System.out.println(nextT());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

//    @Override
    public Expression nextT() throws Exception {
        Expression rst = null;
        currentToken = tokenizer.next();
        Expression left = combinator();
        Token<Integer, String> token = tokenizer.next();
        if (token.type() == '(') { //e.g. name(...)

        } else if (token.type() == '{') { //e.g.

        } else if (token.type() == '[') {

        }
        System.out.println("i am Parser");
        return null;
    }

    private Token<Integer, String> nextToken() {
        currentToken = tokenizer.next();
        return currentToken;
    }

    private Token<Integer, String> getToken() {
        return currentToken;
    }

    private Expression expression() throws Exception {
        Expression expression = combine(combinator());
        if (Application.isOperator(getToken().value())) { //e.g. expression op ...
            expression = operator(expression, new SymbolExpression(getToken().value()));
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
        if (Application.isOperator(getToken().value())) { //e.g. left op right op2 ...
            Expression op2 = new SymbolExpression(getToken().value());
            if (Application.getPriority(op.toString(), getToken().value()) < 0) { //e.g. left op (right op2 ...)
                return new EvalExpression(op, new ListExpression(left, operator(right, op2)));
            } else { //e.g. (left op right) op2 ...
                return operator(new EvalExpression(op, new ListExpression(left, right)),op2);
            } 
        } else { //e.g. left op right
            return new EvalExpression(op, new ListExpression(left, right));
        }
    }

    /**
     * 生成一个表达式
     * e.g. combinator() || name(...) || name(...)(...) || name(...){...} || (...){...}(...)(...) || ...
     * @return
     */
    private Expression combine(Expression left) throws Exception {
        if (getToken().type() == '(') { //e.g. left(...)...
            ListExpression paren = parenExpression();
            if (getToken().type() == '{') { //e.g. left(...){...}
                Unit<Name, Expression> unit = braceExpression();
                unit.setParameter(paren.toParameter());
                if (left instanceof Name) {
                    return new DefaultPair((Name)left, unit);
                } else {
                    throw new Exceptions.ExpressionNotSurpportException();
                }
            } else if (getToken().type() == '(') { //e.g. left(...)(...)...
                return combine(new EvalExpression(left,paren));
            } else { //e.g. left(...)
                return new EvalExpression(left, paren);

            }
        } else if (getToken().type() == '{') { //e.g. left{...}...
            Unit<Name, Expression> unit = braceExpression();
            if (left instanceof Name) { //e.g. name{...}
                return new DefaultPair((Name)left, unit);
            } else if (left instanceof ListExpression){ //e.g. (...){...}...
                unit.setParameter(((ListExpression) left).toParameter());
                return combine(unit);
            } else {
                throw new Exceptions.ExpressionNotSurpportException();
            }

        } else if (getToken().type() == ':') { //e.g. left: ...
            nextToken();
            return new DefaultPair((Name) left, expression());
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
        if(getToken().type() == Constants.SYMBOL){ //e.g. name...
            Expression expression = new SymbolExpression(getToken().value());
            if (Application.isOperator(getToken().value())) { //e.g. ! a
                nextToken(); //eat
                return new EvalExpression(expression, new ListExpression(combinator()));
            }
            nextToken(); //eat left
            return expression;
        } else if (getToken().type() == Constants.STRING) { //e.g. "name"...
            Expression expression = new StringExpression(getToken().value());
            nextToken(); //eat
            return expression;
        } else if (getToken().type() == Constants.NUMBER) { //e.g. 123...
            Expression expression = new NumberExpression(getToken().value());
            nextToken(); //eat
            return expression;
        } else if (getToken().type() == '(' || getToken().type() == Constants.SPACE_LEFT_PAREN) { //e.g. (...)...
            return parenExpression();
        } else if (getToken().type() == '[' || getToken().type() == Constants.SPACE_LEFT_BRACKET) { //e.g. [...]...
            return bracketExpression();
        } else if (getToken().type() == '{' || getToken().type() == Constants.SPACE_LEFT_BRACE) { //e.g. {...}...
            return braceExpression();
        } else if (getToken().type() == Constants.NIL){ //文件结尾
            return ExpressionTypeMode.NIL;
        } else { //其他字符跳过
            nextToken(); //eat
            return combinator();
        }

    }

    /**
     * e.g. {...}
     * @return
     */
    private Unit braceExpression() throws Exception {
        nextToken(); //eat '{'
        Unit<Name, Expression> unit = new DefaultUnit();
        while (getToken().type() != '}') {
            parseDomain(unit);
        }
        nextToken(); //eat '}'
        return unit;
    }

    /**
     * 解析一个表达式放入unit
     * @return
     */
    private void parseDomain(Unit<Name, Expression> unit) throws Exception {
        Expression expression = expression();
        if (expression instanceof Pair) {
            unit.appendChild(((Pair<Name, Expression>) expression).key(),((Pair<Name, Expression>) expression).value());
        } else {
            unit.appendEvalStream(expression);
        }
    }

    /**
     * e.g. [...]
     * @return
     */
    private ListExpression bracketExpression() throws Exception {
        nextToken(); //eat '['
        ListExpression listExpression = new ListExpression();
        while (getToken().type() != ']') {
            listExpression.addExpression(expression());
        }

        nextToken(); //eat ']'
        return listExpression;
    }

    /**
     * e.g. (...)
     * @return
     */
    private ListExpression parenExpression() throws Exception {
        nextToken(); //eat '('
        ListExpression listExpression = new ListExpression();
        while (getToken().type() != ')') {
            listExpression.addExpression(expression());
        }
        return listExpression;
    }


    @Override
    public boolean hasNext() {
        return tokenizer.hasNext();
    }

    @Override
    public void init(Reader reader) {
        tokenizer.init(reader);
    }

    @Override
    public void close() throws IOException {
        tokenizer.close();
    }
}
