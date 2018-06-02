package fun.mandy;

import fun.mandy.util.StaticUtils;

import java.io.Serializable;

class Model implements Serializable {
    public String name;
    public transient String age;

    public Model(String name, String age) {
        this.name = name;
        this.age = age;
    }
}

public class CommonTest {
    public static void main( String[] args ) {
        Model t1 = new Model("zhangsan", "22");
        Model t2 = StaticUtils.copy(t1);
        System.out.println();
    }

}
