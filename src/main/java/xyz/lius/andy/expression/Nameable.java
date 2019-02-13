package xyz.lius.andy.expression;

public interface Nameable {
    /**
     * transfer an object to a name
     * @return
     */
    default Name getName() {
        return Name.NIL;
    }
}
