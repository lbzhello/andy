package xyz.lbzh.andy.util;

public class Pointer<T> {
    private T obj;

    public Pointer() {}

    public Pointer(T obj) {
        this.obj = obj;
    }

    public T getObj() {
        return obj;
    }

    public void setObj(T obj) {
        this.obj = obj;
    }
}
