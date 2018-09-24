package xyz.lbzh.andy.expression.constant;

import xyz.lbzh.andy.expression.Expression;

public enum ExpressionType implements Expression {
    NIL, DEFINE, PAIR, LAMBDA, EOF, HOF
}
