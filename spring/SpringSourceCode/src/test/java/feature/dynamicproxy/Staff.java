package feature.dynamicproxy;

import java.math.BigDecimal;

public class Staff implements Person {

    private String name;

    public Staff(String name) {
        this.name = name;
    }

    @Override
    public BigDecimal orderFood(String food) {
        System.out.println(this.name+" ordered "+food+"!");
        return new BigDecimal(99.8);
    }
}

