package anatomy;

import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import java.util.UUID;

public class Util {
    public static void stringjoiner() {
        StringJoiner joiner = new StringJoiner("->", "[", "]");
        List<String> list = new ArrayList<>();
        list.add("Michael");
        list.add("Jack");
        list.add("Mike");
        for (String s : list) {
            joiner.add(s);
        }
        System.out.println(joiner.toString());
    }

    public static void main(String[] args) {
//        Util.stringjoiner();
        System.out.println(UUID.randomUUID().toString().replaceAll("-", "").length());
    }

}
