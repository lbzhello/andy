package xyz.lius.andy.expression;

import xyz.lius.andy.core.Definition;
import xyz.lius.andy.expression.operator.*;

/**
 * Supply native function
 */
public class NativeFactory {
    public static AssignExpression assign() {
        return new AssignExpression();
    }

    public static PlusExpression plus() {
        return new PlusExpression();
    }

    public static MinusExpression minus() {
        return new MinusExpression();
    }

    public static MultiplyExpression Multiply() {
        return new MultiplyExpression();
    }

    public static DivideExpression divide() {
        return new DivideExpression();
    }

    public static EqualExpression equal() {
        return new EqualExpression();
    }

    public static NotEqualExpression notEqual() {
        return new NotEqualExpression();
    }

    public static GtExpression greaterThan() {
        return new GtExpression();
    }

    public static GeExpression greaterEqual() {
        return new GeExpression();
    }

    public static LtExpression lessThan() {
        return new LtExpression();
    }

    public static LeExpression lessEqual() {
        return new LeExpression();
    }

    public static OrExpression or() {
        return new OrExpression();
    }

    public static Expression trueExpression() {
        return Definition.TRUE;
    }

    public static Expression falseExpression() {
        return Definition.FALSE;
    }

    public static Expression nil() {
        return Definition.NIL;
    }

    public static ReturnExpression returnExpression() {
        return new ReturnExpression();
    }

    public static IfExpression ifExpression() {
        return new IfExpression();
    }

    public static ForExpression forExpression() {
        return new ForExpression();
    }

    public static ArrowExpression arrow() {
        return new ArrowExpression();
    }

    public static PrintExpression print() {
        return new PrintExpression();
    }

    public static NewExpression newExpression() {
        return new NewExpression();
    }

    public static ImportExpression importExpression() {
        return new ImportExpression();
    }

    public static FileExpression file() {
        return new FileExpression();
    }
}
