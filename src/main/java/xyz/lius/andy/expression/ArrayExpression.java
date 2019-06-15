package xyz.lius.andy.expression;

import xyz.lius.andy.expression.operator.ArrayMethod;

/**
 * 数组方法,实现此接口可以通过ArrayMethod调用
 * @see ArrayMethod
 */
public interface ArrayExpression extends Expression {
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
