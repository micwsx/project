package com.research.java8.CompletableFutureClass;

public class Discount {
    public enum Code {
        NONE(0), SILVER(5), GOLD(10), PLATINUM(15), DIAMOND(20);

        private int percentage;

        private Code(int percentage) {
            this.percentage = percentage;
        }

        public int getPercentage() {
            return percentage;
        }
    }

    public static String applyDiscount(Quote quote) {
        String s = apply(quote.getShopPrice(), quote.getCode());
        return quote.getShopName() + " price is " + s;
    }

    public static String apply(double price, Code code) {
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return String.format("%.2f", price * ((100 - code.percentage) * 0.1 / 100));
    }
}
