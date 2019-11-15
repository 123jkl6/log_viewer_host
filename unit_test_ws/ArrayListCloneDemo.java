import java.util.*;
import java.lang.CloneNotSupportedException;

public class ArrayListCloneDemo {

    public static void main(String[] args) throws CloneNotSupportedException {
        ArrayList<String> original = new ArrayList<String>(){
            {
                add("string1");
                add("string2");
                add("string3");
                add("string4");
                add("string5");
            }
        };

        ArrayList<String> copy = (ArrayList<String>) original.clone();

        copy.set(1,"string2changed");

        System.out.println("original : "+original);
        System.out.println("copy : "+copy);
    }

}