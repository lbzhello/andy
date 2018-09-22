package xyz.lbzh.andy.engine;


public interface Engine {
    Object eval(Object expression);
    Object build(Object expression);
}
