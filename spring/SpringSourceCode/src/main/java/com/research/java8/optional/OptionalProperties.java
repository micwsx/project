package com.research.java8.optional;

import javax.swing.text.html.Option;
import java.util.Optional;
import java.util.Properties;

public class OptionalProperties {
    public static void main(String[] args) {

        Properties properties = new Properties();
        properties.put("a", 5);
        properties.put("b", true);
        properties.put("c", -3);

        System.out.println(readDurationOptional(properties,"b"));

    }

    public static int readDurationOptional(Properties properties, String name) {
        String value = properties.getProperty(name);
        return Optional.ofNullable(value)
                .flatMap(s -> {
                    try {
                        return Optional.of(Integer.parseInt(s));
                    } catch (NumberFormatException e) {
                        return Optional.empty();
                    }
                }).filter(i -> i > 0).orElse(0);
    }


    public static int readDuration(Properties properties, String name) {
        String value = properties.getProperty(name);
        if (value != null) {
            try {
                int i = Integer.parseInt(value);
                if (i > 0)
                    return i;
            } catch (NumberFormatException e) {
            }
        }
        return 0;
    }
}
