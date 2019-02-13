package xyz.lius.andy.expression;

/**
 * 表达式数组迭代接口,实现此接口可以通过 ArrayMethodExpression MethodHandle 方式调用
 * e.g. [expr1 expr2 expr3 ...]
 * @see xyz.lius.andy.expression.runtime.ArrayMethodExpression
 */
public interface ExpressionArray extends Expression {
    //x -> y
    Expression map(Expression func);

    //x -> nil
    Expression each(Expression func);

    //x -> boolean
    Expression filter(Expression func);

    //[x, y] -> [x, y -> z]
    Expression mapValues(Expression func);

    //[x, y] -> z
    Expression reduce(Expression func);

    //[[x, y] [x, z] ...] -> [[x, y, z] ...] -> [[x, [y, z] -> a] ...]
    Expression reduceByKey(Expression func);

    //x -> [k x]
    Expression groupBy(Expression func);

    //[[x, y] [x, z] ...] -> [[x, y, z] ...]
    Expression groupByKey();

    Expression first();

    Expression other();

    Expression reverse();

    Expression count();
}