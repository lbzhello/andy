package xyz.lbzh.andy.expression;

public enum ExpressionType implements Expression {
    NIL, SELF, DEFINE, PAIR, LAMBDA, PARENT,
    PLUS, MINUS, MULTIPLY, DIVIDE,
    OR, AND, NOT,
    TRUE, FALSE,
    PRINT,
    ARRAY,
}
