package fun.mandy.parser.support;

import fun.mandy.core.Definition;
import fun.mandy.exception.Exceptions;
import fun.mandy.expression.Expression;
import fun.mandy.expression.Name;
import fun.mandy.expression.support.*;
import fun.mandy.parser.Parser;
import fun.mandy.tokenizer.Tokenizer;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DefaultParser implements Parser<Expression> {
    private Tokenizer<ValueExpression> tokenizer;

    private ValueExpression currentToken = Definition.HOF;

    public DefaultParser(Tokenizer<ValueExpression> tokenizer) {
        this.tokenizer = tokenizer;
    }

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

    private ValueExpression nextToken() {
        currentToken = tokenizer.next();
        return currentToken;
    }

    private ValueExpression getToken() {
        return currentToken;
    }

    private Expression expression() throws Exception {
        Expression expression = combine(combinator());
        if (Definition.isOperator(getToken().getValue())) { //e.g. expression op ...
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
        if (Definition.isOperator(getToken().getValue())) { //e.g. left op right op2 ...
            Expression op2 = getToken();
            if (Definition.getPriority(op.toString(), op2.toString()) < 0) { //e.g. left op (right op2 ...)
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
        if (left instanceof ValueExpression && Definition.isCommand(((ValueExpression) left).getValue())) {
            return commandExpression((ValueExpression) left);
        }
        if (Objects.equals(getToken().getValue(), "(")) { //e.g. left(...)...
            EvalExpression evalExpression = new EvalExpression(left, parenExpression().getList());
            if (Objects.equals(getToken().getValue(), "{")) { //e.g. left(...){...}
                GroupExpression group = braceExpression();
                if (left instanceof Name) {
                    return new EvalExpression(Definition.DEFINE,evalExpression,group);
                } else {
                    throw new Exceptions.ExpressionNotSurpportException();
                }
            } else if (Objects.equals(getToken().getValue(), "(")) { //e.g. left(...)(...)...
                return combine(evalExpression);
            } else { //e.g. left(...)
                return evalExpression;

            }
        } else if (Objects.equals(getToken().getValue(), "{")) { //e.g. left{...}...
            GroupExpression group = braceExpression();
            if (left instanceof Name) { //e.g. name{...}
//                return new DefaultPair((Name)left, unit);
                return new EvalExpression(Definition.DEFINE,left, group);
            } else if (left instanceof ListExpression){ //e.g. (...){...}...
                return combine(new EvalExpression(Definition.LAMBDA, left, group));
            } else {
                throw new Exceptions.ExpressionNotSurpportException();
            }

        } else if (Objects.equals(getToken().getValue(), ":")) { //e.g. left: ...
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
            if (Definition.isOperator(getToken().getValue())) { //e.g. ! a
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
        } else if (Objects.equals(getToken().getValue(), "(") || Objects.equals(getToken().getValue(), Definition.SPACE + "(")) { //e.g. (...)...
            return parenExpression();
        } else if (Objects.equals(getToken().getValue(), "[") || Objects.equals(getToken().getValue(), Definition.SPACE + "[")) { //e.g. [...]...
            return bracketExpression();
        } else if (Objects.equals(getToken().getValue(), "{") || Objects.equals(getToken().getValue(), Definition.SPACE + "{")) { //e.g. {...}...
            return braceExpression();
        } else if (getToken() == Definition.EOF){ //文件结尾
            return Definition.EOF;
        } else { //其他字符跳过
            nextToken(); //eat
            return combinator();
        }

    }

    private Expression commandExpression(ValueExpression cmd) throws Exception {
        int size = Definition.getCommandSize(cmd.getValue());
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
    private GroupExpression braceExpression() throws Exception {
        nextToken(); //eat '{'
        GroupExpression groupExpression = new GroupExpression();
        while (!Objects.equals(getToken().getValue(), "}")) {
            parseGroup(groupExpression);
        }
        nextToken(); //eat '}'
        return groupExpression;
    }

    /**
     * 解析一个表达式放入unit
     * @return
     */
    private void parseGroup(GroupExpression groupExpression) throws Exception {
        Expression expression = expression();
        if (expression instanceof EvalExpression) {
            EvalExpression evalExpression = (EvalExpression)expression;
            if (Objects.equals(evalExpression.head().toString(), Definition.DEFINE)) { //对象定义
                groupExpression.addBuildStream(expression);
            } else if (Objects.equals(evalExpression.head().toString(), Definition.COLON)) { //字段定义
                groupExpression.addBuildStream(expression);
            } else {
                groupExpression.addEvalStream(expression);
            }
        } else {
            groupExpression.addEvalStream(expression);
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
        while (!Objects.equals(getToken().getValue(), "]")) {
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
        while (!Objects.equals(getToken().getValue(), ")")) {
            Expression expression = expression();
            if (!Objects.equals(getToken().getValue(), ",") && !Objects.equals(getToken().getValue(), ")")) { //e.g. (expr1 expr2 ...)
                List<Expression> paramList = new ArrayList<>();
                while (!Objects.equals(getToken().getValue(), ",") && !Objects.equals(getToken().getValue(), ")")) {
                    paramList.add(getToken());
                    nextToken();
                }
                expression = new EvalExpression(expression, paramList);
            }
            if (Objects.equals(getToken().getValue(), "," )) nextToken(); //eat ","
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
    public void init(Reader reader) {
        tokenizer.init(reader);
    }

    @Override
    public void close() throws IOException {
        tokenizer.close();
    }
}
