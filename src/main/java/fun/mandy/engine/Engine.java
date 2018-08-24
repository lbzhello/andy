package fun.mandy.engine;


public interface Engine {
    Object eval(Object expression);
    Object build(Object expression);
}
