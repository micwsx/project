package com.research.java8.CompletableFutureClass;

public class Quote {
    private final String shopName;
    private final double shopPrice;
    private final Discount.Code code;

    public Quote(String shopName, double shopPrice, Discount.Code code) {
        this.shopName = shopName;
        this.shopPrice = shopPrice;
        this.code = code;
    }

    public static Quote parse(String s){
        String[] split=s.split(":");
        String shopName = split[0];
        double price = Double.parseDouble(split[1]);
        Discount.Code code = Discount.Code.valueOf(split[2]);
        return new Quote(shopName,price,code);
    }

    public String getShopName() {
        return shopName;
    }

    public double getShopPrice() {
        return shopPrice;
    }

    public Discount.Code getCode() {
        return code;
    }
}
