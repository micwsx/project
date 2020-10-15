package com.research.rpc;

/**
 * @author Michael
 * @create 9/14/2020 3:03 PM
 */
public class ProductImp implements Product {
    @Override
    public String getPrice(String name) {
        return "商品"+name+"$100";
    }
}
