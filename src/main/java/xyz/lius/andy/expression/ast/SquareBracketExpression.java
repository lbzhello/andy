package xyz.lius.andy.expression.ast;

import xyz.lius.andy.expression.*;

import java.util.*;

/**
 * [...]
 */
public class SquareBracketExpression extends BracketExpression implements ArrayExpression {

    public SquareBracketExpression(Expression... expressions) {
        super(expressions);
    }

    @Override
    public Expression eval(Context<Name, Expression> context) {
//        BracketExpression rstList = ExpressionFactory.squareBracket();
//        for (Expression element : setCodes()) {
//            rstList.add(element.eval(context));
//        }
//        return rstList;
        return this;
    }

    @Override
    public String toString() {
        return "[" + super.toString() + "]";
    }

    //setCodes operation
    @Override
    public Expression map(Expression func) {
        BracketExpression squareBracket = ExpressionFactory.squareBracket();
        StackFrame stackFrame = new StackFrame((Complex) func);
        for (Expression expression : this.list()) {
            stackFrame.add(ExpressionFactory.symbol("$0"), expression);
            squareBracket.add(stackFrame.eval(null));
        }
        return squareBracket;
    }

    @Override
    public Expression each(Expression func) {
        StackFrame stackFrame = new StackFrame((Complex) func);
        for (Expression expression : this.list()) {
            stackFrame.add(ExpressionFactory.symbol("$0"), expression);
            stackFrame.eval(null);
        }
        return ExpressionType.NIL;
    }

    @Override
    public Expression filter(Expression func) {
        BracketExpression squareBracket = ExpressionFactory.squareBracket();
        StackFrame stackFrame = new StackFrame((Complex) func);
        for (Expression expression : this.list()) {
            stackFrame.add(ExpressionFactory.symbol("$0"), expression);
            if (stackFrame.eval(null) == ExpressionType.TRUE) {
                squareBracket.add(expression);
            }
        }
        return squareBracket;
    }

    @Override
    public Expression mapValues(Expression func) {
        SquareBracketExpression squareBracket = ExpressionFactory.squareBracket();
        StackFrame stackFrame = new StackFrame((Complex) func);
        for (Expression expression : list()) {
            ArrayExpression array = ExpressionUtils.asArray(expression);
            stackFrame.add(ExpressionFactory.symbol("$0"), array.other());
            squareBracket.add(ExpressionFactory.squareBracket(array.first(), stackFrame.eval(null)));
        }
        return squareBracket;
    }

    @Override
    public Expression reduce(Expression func) {
        if (list().size() < 2) {
            return ExpressionType.NIL;
        } else {
            StackFrame stackFrame = new StackFrame((Complex) func);
            Iterator<Expression> iterator = list().iterator();
            Expression acc = iterator.next();
            while (iterator.hasNext()) {
                stackFrame.add(ExpressionFactory.symbol("$0"), acc);
                stackFrame.add(ExpressionFactory.symbol("$1"), iterator.next());
                acc = stackFrame.eval(null);
            }
            return acc;
        }
    }

    /**
     *
     * @param iterator next expression
     * @param func  reduce function
     * @param context avoid create unnecessary contexts
     * @return
     */
    private Expression reduceCircle(Iterator<Expression> iterator, Expression func, Context<Name, Expression> context) {
        context.add(ExpressionFactory.symbol("$1"), iterator.next()); //绑定第二个参数
        Expression first = func.eval(context);
        if (iterator.hasNext()) {
            context.add(ExpressionFactory.symbol("$0"), first);
            return reduceCircle(iterator, func, context);
        } else {
            return first;
        }

    }

    @Override
    public Expression reduceByKey(Expression func) {
        SquareBracketExpression squareBracket = ExpressionUtils.asSquareBracket(groupByKey());
        SquareBracketExpression rst = ExpressionFactory.squareBracket();
        for (Expression expression : squareBracket.list()) { //e.g. [[...] [...] [...]]
            ArrayExpression array = ExpressionUtils.asArray(expression);
            Expression first = array.first();
            Expression other = array.other();
            if (ExpressionUtils.isArray(other)) {
                other = ExpressionUtils.asArray(other).reduce(func);
            }
            rst.add(ExpressionFactory.squareBracket(first, other));
        }
        return rst;
    }

    @Override
    public Expression groupBy(Expression func) {
        Map<Expression, SquareBracketExpression> keyMap = new HashMap<>();
        SquareBracketExpression parrent = ExpressionFactory.squareBracket(); //rst [[...] [...]]
        StackFrame stackFrame = new StackFrame((Complex) func);
        for (Expression element : list()) {
            stackFrame.add(ExpressionFactory.symbol("$0"), element); //传参
            Expression key = stackFrame.eval(null);
            SquareBracketExpression child = keyMap.get(key);
            if (child == null) { //new
                child = ExpressionFactory.squareBracket(key, element);
                parrent.add(child);
                keyMap.put(key, child);
            } else {
                child.add(element);
            }
        }
        return parrent;
    }

    @Override
    public Expression groupByKey() {
        Map<Expression, SquareBracketExpression> keyMap = new HashMap<>();
        SquareBracketExpression parent = ExpressionFactory.squareBracket(); //[[...] [...] [...]]
        Iterator<Expression> iterator = list().iterator();
        while (iterator.hasNext()) {
            ArrayExpression array = ExpressionUtils.asArray(iterator.next());
            Expression key = array.first();
            Expression value = array.other();
            SquareBracketExpression child = keyMap.get(key); //[...]
            if (child == null) { //new key
                child = ExpressionFactory.squareBracket();
                child.add(key); child.add(value);
                parent.add(child);
                keyMap.put(key, child); //记录
            } else { //group
                child.add(value);
            }
        }
        return parent;
    }

    @Override
    public Expression first() {
        return get(0);
    }

    @Override
    public Expression other() {
        List<Expression> rst = this.list().size() >= 2 ? this.list().subList(1, this.list().size()) : Collections.emptyList();
        if (rst.size() == 0) { //e.g. []
            return ExpressionType.NIL;
        } else if (rst.size() == 1) { //e.g. [first second]
            return rst.get(0);
        }
        return ExpressionFactory.squareBracket().list(rst);
    }

    @Override
    public Expression reverse() {
        BracketExpression squareBracket = ExpressionFactory.squareBracket();
        for (int i = list().size() - 1; i >= 0; i--) {
            squareBracket.add(list().get(i));
        }
        return squareBracket;
    }

    @Override
    public Expression count() {
        return ExpressionFactory.number(list().size());
    }
}
