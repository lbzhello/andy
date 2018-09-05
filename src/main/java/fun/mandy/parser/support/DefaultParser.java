package fun.mandy.parser.support;

import fun.mandy.core.Definition;
import fun.mandy.exception.Exceptions;
import fun.mandy.expression.Expression;
import fun.mandy.expression.Name;
import fun.mandy.expression.support.*;
import fun.mandy.parser.Parser;
import fun.mandy.tokenizer.Tokenizer;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DefaultParser implements Parser<Expression> {
    private Tokenizer<Expression> tokenizer;

    private Expression currentToken = Definition.HOF;

    public DefaultParser(Tokenizer<Expression> tokenizer) {
        this.tokenizer = tokenizer;
    }

    @Override
    public Expression parse(String fileName){
        ComplexExpression complexExpression = new ComplexExpression();
        try {
            Reader reader = new FileReader(fileName);
            tokenizer.init(reader);
            while (hasNext()) {
                parseComplex(complexExpression);
            }
            tokenizer.close();
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return complexExpression;
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
        if (Definition.isOperator(getToken().toString())) { //e.g. expression op ...
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
        if (Definition.isOperator(getToken().toString())) { //e.g. left op right op2 ...
            Expression op2 = getToken();
            if (Definition.comparePriority(op.toString(), op2.toString()) < 0) { //e.g. left op (right op2 ...)
                return new EvalExpression(op, left, operator(right, op2));
            } else { //e.g. (left op right) op2 ...
                return operator(new EvalExpression(op, left, right),op2);
            } 
        } else { //e.g. left op right
            return new EvalExpression(op, left, right);
        }
    }

    /**
     * Generate an expressione
     * e.g. combinator() || name(...) || name(...)(...) || name(...){...} || (...){...}(...)(...) || var a ||...
     * @return
     */
    private Expression combine(Expression left) throws Exception {
        //if it's command
        if (Definition.isCommand(left.toString())) {
            return commandExpression(left);
        }
        if (Objects.equals(getToken().toString(), "(")) { //e.g. left(...)...
            EvalExpression evalExpression = new EvalExpression(left, parenExpression().getList());
            if (Objects.equals(getToken().toString(), "{")) { //e.g. left(...){...}
                ComplexExpression group = braceExpression();
                if (left instanceof Name) {
                    return new EvalExpression(Definition.DEFINE,evalExpression,group);
                } else {
                    throw new Exceptions.ExpressionNotSurpportException();
                }
            } else if (Objects.equals(getToken().toString(), "(")) { //e.g. left(...)(...)...
                return combine(evalExpression);
            } else { //e.g. left(...)
                return evalExpression;

            }
        } else if (Objects.equals(getToken().toString(), "{")) { //e.g. left{...}...
            ComplexExpression group = braceExpression();
            if (left instanceof Name) { //e.g. name{...}
//                return new DefaultPair((Name)left, unit);
                return new EvalExpression(Definition.DEFINE,left, group);
            } else if (left instanceof ListExpression){ //e.g. (...){...}...
                return combine(new EvalExpression(Definition.LAMBDA, left, group));
            } else {
                throw new Exceptions.ExpressionNotSurpportException();
            }

        } else if (Objects.equals(getToken().toString(), ":")) { //e.g. left: ...
            nextToken();
            return new EvalExpression(Definition.COLON, left, expression());
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
            if (Definition.isOperator(getToken().toString())) { //e.g. ! a
                nextToken(); //eat
                return new EvalExpression(expression, combinator());
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
        } else if (Objects.equals(getToken().toString(), "(") || Objects.equals(getToken().toString(), Definition.SPACE + "(")) { //e.g. (...)...
            return parenExpression();
        } else if (Objects.equals(getToken().toString(), "[") || Objects.equals(getToken().toString(), Definition.SPACE + "[")) { //e.g. [...]...
            return bracketExpression();
        } else if (Objects.equals(getToken().toString(), "{") || Objects.equals(getToken().toString(), Definition.SPACE + "{")) { //e.g. {...}...
            return braceExpression();
        } else if (getToken() == Definition.EOF){ //文件结尾
            return Definition.EOF;
        } else { //其他字符跳过
            nextToken(); //eat
            return combinator();
        }

    }

    private Expression commandExpression(Expression cmd) throws Exception {
        int size = Definition.getCommandSize(cmd.toString());
        ListExpression listExpression = new ListExpression();
        for (int i = 0; i < size; i++) {
            listExpression.addExpression(expression());
        }
        return new EvalExpression(cmd, listExpression.getList());
    }

    /**
     * e.g. {...}
     * @return
     */
    private ComplexExpression braceExpression() throws Exception {
        nextToken(); //eat '{'
        ComplexExpression complexExpression = new ComplexExpression();
        while (!Objects.equals(getToken().toString(), "}")) {
            parseComplex(complexExpression);
        }
        nextToken(); //eat '}'
        return complexExpression;
    }

    /**
     * 解析一个表达式放入unit
     * @return
     */
    private void parseComplex(ComplexExpression complexExpression) throws Exception {
        Expression expression = expression();
        if (expression instanceof EvalExpression) {
            EvalExpression evalExpression = (EvalExpression)expression;
            if (Objects.equals(evalExpression.head().toString(), Definition.DEFINE)) { //对象定义
                complexExpression.addBuildStream(expression);
            } else if (Objects.equals(evalExpression.head().toString(), Definition.COLON)) { //字段定义
                complexExpression.addBuildStream(expression);
            } else {
                complexExpression.addEvalStream(expression);
            }
        } else {
            complexExpression.addEvalStream(expression);
        }
    }

//    /**
//     * 解析一个表达式放入unit
//     * @return
//     */
//    private void parseDomain(Unit<Name, Expression> unit) throws Exception {
//        Expression expression = expression();
////        if (expression instanceof Pair) {
////            unit.setChild(((Pair<Name, Expression>) expression).key(),((Pair<Name, Expression>) expression).value());
////        } else
//        if (expression instanceof EvalExpression) {
//            EvalExpression evalExpression = (EvalExpression)expression;
//            if (evalExpression.head().toString().equals(Definition.DEFINE)) { //对象定义
//                unit.addBuildStream(expression);
//                unit.setChild((Name) evalExpression.getList().get(0),evalExpression.getList().get(1));
//            } else if (evalExpression.head().toString().equals(Definition.COLON)) { //字段定义
//                unit.addBuildStream(expression);
//                unit.setChild((Name) evalExpression.getList().get(0),evalExpression.getList().get(1));
//            } else {
//                unit.addEvalStream(expression);
//            }
//        } else {
//            unit.addEvalStream(expression);
//        }
//    }

    /**
     * e.g. [...]
     * @return
     */
    private ListExpression bracketExpression() throws Exception {
        nextToken(); //eat '['
        ListExpression listExpression = new ListExpression();
        while (!Objects.equals(getToken().toString(), "]")) {
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
        while (!Objects.equals(getToken().toString(), ")")) {
            Expression expression = expression();
            if (!Objects.equals(getToken().toString(), ",") && !Objects.equals(getToken().toString(), ")")) { //e.g. (expr1 expr2 ...)
                List<Expression> paramList = new ArrayList<>();
                while (!Objects.equals(getToken().toString(), ",") && !Objects.equals(getToken().toString(), ")")) {
                    paramList.add(getToken());
                    nextToken();
                }
                expression = new EvalExpression(expression, paramList);
            }
            if (Objects.equals(getToken().toString(), "," )) nextToken(); //eat ","
            listExpression.addExpression(expression);
        }
        nextToken(); //eat ")"
        return listExpression;
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
