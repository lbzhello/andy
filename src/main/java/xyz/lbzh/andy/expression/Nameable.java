package xyz.lbzh.andy.expression;

public interface Nameable {
    default Name name() {
        return Name.NIL;
    }
}
