package xyz.lius.andy.expression;

//e.g. (max a b)
public interface Operator extends Expression {
    Operator NIL = new Constant("nil");
    Operator TRUE = new Constant("true");
    Operator FALSE = new Constant("false");

    void add(Expression operand);

    //implements operator constant
    class Constant implements Operator {
        public Constant(String name) {
            this.name = name;
        }

        private String name;

        @Override
        public Expression eval(Context<Name, Expression> context) {
            return this;
        }

        @Override
        public void add(Expression operand) {}

        @Override
        public String toString() {
            return name;
        }
    }
}
