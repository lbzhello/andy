package xyz.lbzh.andy.expression.support;

import xyz.lbzh.andy.expression.*;

/**
 * (...)
 */
public class RoundBracketExpression extends BracketExpression {

    public RoundBracketExpression(Expression... expressions) {
        super(expressions);
    }

    public static RoundBracketExpression operator(Expression... expressions) {
        return new OperatorExpression(expressions);
    }

    @Override
    public Name toName() {
        return this.first().toName();
    }

    @Override
    public String toString() {
        return "(" + super.toString() + ")";
    }

    @RoundBracketed
    private static class OperatorExpression extends RoundBracketExpression {
        public OperatorExpression(Expression... expressions) {
            super(expressions);
        }

    }

    private static class DefinitionExpression extends RoundBracketExpression {
        private Expression name;
        private Expression body;
        public DefinitionExpression(Expression name, Expression body) {
            super(name, body);
            this.name = name;
            this.body = body;
        }

        @Override
        public Expression eval(Context<Name, Object> context) {
            context.bind(name.toName(), body);
            return null;
        }
    }

}
