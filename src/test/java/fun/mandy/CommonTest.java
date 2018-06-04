package fun.mandy;

import java.io.Serializable;

class Model implements Serializable {
    public String name;
    public transient String age;

    public Model(String name, String age) {
        this.name = name;
        this.age = age;
    }

    public static boolean parseNum(String num){
        try {
            Double d = Double.valueOf(num);
            System.out.println(d);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}

public class CommonTest {
    public static void main( String[] args ) {

        System.out.println(Character.isLetter('/'));
    }

}
