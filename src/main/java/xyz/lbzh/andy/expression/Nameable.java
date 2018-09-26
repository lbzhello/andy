package xyz.lbzh.andy.expression;

public interface Nameable {
    /**
     * transfer an object to a name
     * @return
     */
    default Name toName() {
        return Name.NIL;
    }
}
