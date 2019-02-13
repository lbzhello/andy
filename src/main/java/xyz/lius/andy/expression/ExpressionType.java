package xyz.lius.andy.expression;

public enum ExpressionType implements Expression {
    NIL, DEFINE, PAIR, LAMBDA, PARENT,
    PLUS, MINUS, MULTIPLY, DIVIDE,
    OR, AND, NOT,
    TRUE, FALSE,
    PRINT,
    ARRAY,
}
