package com.research.java8.ImproveCodeFlexibility;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * Strategy,Template,Observer, Chain of Responsibility,Factory
 */
public class FactoryTolambda {

    private static Map<String, Supplier<Product>> map = new HashMap<String, Supplier<Product>>() {
        {
            put("load", Loan::new);
            put("bond", Bond::new);
            put("stock", Stock::new);
        }
    };

    public static void main(String[] args) {

        Product product = createSupplierProduct("load");
        System.out.println(product);
//        Product product=ProductFactory.createProduct("load");
//        System.out.println(product);
    }

    public static Product createSupplierProduct(String name) {
        Supplier<Product> supplier = map.get(name);
        if (supplier != null) return supplier.get();
        throw new UnsupportedOperationException();
    }
}

class ProductFactory {


    public static Product createProduct(String name) {
        String key = name.toLowerCase();
        if (key == "load")
            return new Loan();
        else if (key == "bond")
            return new Bond();
        else if (key == "stock")
            return new Stock();
        else
            throw new UnsupportedOperationException();
    }
}

abstract class Product {
}

class Loan extends Product {
}

class Stock extends Product {
}

class Bond extends Product {
}


