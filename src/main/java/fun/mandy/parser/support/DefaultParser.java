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
import fun.mandy.tokenizer.Tokenizer;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.Reader;

@Named
public class DefaultParser implements Parser<Expression> {
    @Inject
    private Tokenizer<Expression> tokenizer;

    private Expression currentToken = ExpressionTypeMode.BEGIN;

    @Override
    public Expression next(){
        while (hasNext()) {
            try {
                Expression expression = expression();
                System.out.println(expression);
            } catch (Exception e) {
                e.printStackTrace();
            }
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
        if (Application.isOperator(getToken().toString())) { //e.g. expression op ...
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
        if (Application.isOperator(getToken().toString())) { //e.g. left op right op2 ...
            Expression op2 = getToken();
            if (Application.getPriority(op.toString(), op2.toString()) < 0) { //e.g. left op (right op2 ...)
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
        if (getToken().toString().equals("(")) { //e.g. left(...)...
            ListExpression paren = parenExpression();
            if (getToken().toString().equals("{")) { //e.g. left(...){...}
                Unit<Name, Expression> unit = braceExpression();
                unit.setParameter(paren.toParameter());
                if (left instanceof Name) {
                    return new DefaultPair((Name)left, unit);
                } else {
                    throw new Exceptions.ExpressionNotSurpportException();
                }
            } else if (getToken().toString().equals("(")) { //e.g. left(...)(...)...
                return combine(new EvalExpression(left,paren));
            } else { //e.g. left(...)
                return new EvalExpression(left, paren);

            }
        } else if (getToken().toString().equals("{")) { //e.g. left{...}...
            Unit<Name, Expression> unit = braceExpression();
            if (left instanceof Name) { //e.g. name{...}
                return new DefaultPair((Name)left, unit);
            } else if (left instanceof ListExpression){ //e.g. (...){...}...
                unit.setParameter(((ListExpression) left).toParameter());
                return combine(unit);
            } else {
                throw new Exceptions.ExpressionNotSurpportException();
            }

        } else if (getToken().toString().equals(":")) { //e.g. left: ...
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
        if(getToken() instanceof SymbolExpression){ //e.g. name...
            Expression expression = getToken();
            if (Application.isOperator(getToken().toString())) { //e.g. ! a
                nextToken(); //eat
                return new EvalExpression(expression, new ListExpression(combinator()));
            }
            nextToken(); //eat left
            return expression;
        } else if (getToken() instanceof StringExpression) { //e.g. "name"...
            Expression expression = getToken();
            nextToken(); //eat
            return expression;
        } else if (getToken() instanceof NumberExpression) { //e.g. 123...
            Expression expression = getToken();
            nextToken(); //eat
            return expression;
        } else if (getToken().toString().equals("(") || getToken().toString().equals(Constants.SPACE + "(")) { //e.g. (...)...
            return parenExpression();
        } else if (getToken().toString().equals("[") || getToken().toString().equals(Constants.SPACE + "[")) { //e.g. [...]...
            return bracketExpression();
        } else if (getToken().toString().equals("{") || getToken().toString().equals(Constants.SPACE + "{")) { //e.g. {...}...
            return braceExpression();
        } else if (getToken() == ExpressionTypeMode.NIL){ //文件结尾
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
        while (!getToken().toString().equals("}")) {
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
            unit.setChild(((Pair<Name, Expression>) expression).key(),((Pair<Name, Expression>) expression).value());
        } else {
            unit.addEvalStream(expression);
        }
    }

    /**
     * e.g. [...]
     * @return
     */
    private ListExpression bracketExpression() throws Exception {
        nextToken(); //eat '['
        ListExpression listExpression = new ListExpression();
        while (!getToken().toString().equals("]")) {
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
        while (!getToken().toString().equals(")")) {
            Expression expression = expression();
            if (!getToken().toString().equals(",") && !getToken().toString().equals(")")) { //e.g. (expr1 expr2 ...)
                ListExpression paramList = new ListExpression();
                while (!getToken().toString().equals(",") && !getToken().toString().equals(")")) {
                    paramList.addExpression(getToken());
                    nextToken();
                }
                expression = new EvalExpression(expression, listExpression);
            }
            if (getToken().toString().equals("," )) nextToken(); //eat ","
            listExpression.addExpression(expression);
        }
        nextToken(); //eat ")"
        return listExpression;
    }


    @Override
    public boolean hasNext() {
        return getToken() != ExpressionTypeMode.NIL;
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
