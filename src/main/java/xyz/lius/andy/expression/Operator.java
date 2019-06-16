package xyz.lius.andy.expression;

//e.g. (max a b)
public interface Operator extends Expression {

    void add(Expression operand);

    //implements operator constant
    class Constant implements Operator {
        public static final Operator NIL = new Constant("nil");
        public static final Operator TRUE = new Constant("true");
        public static final Operator FALSE = new Constant("false");

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
