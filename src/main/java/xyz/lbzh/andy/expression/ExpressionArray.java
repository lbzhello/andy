package xyz.lbzh.andy.expression;

/**
 * 表达式数组迭代接口,实现此接口可以通过 ArrayMethodExpression MethodHandle 方式调用
 * e.g. [expr1 expr2 expr3 ...]
 * @see xyz.lbzh.andy.expression.runtime.ArrayMethodExpression
 */
public interface ExpressionArray extends Expression {
    Expression map(Expression func);

    Expression each(Expression func);

    Expression filter(Expression func);

    Expression reduce(Expression func);

    Expression groupByKey();

    Expression first();

    Expression other();

    Expression reverse();

    Expression count();
}
