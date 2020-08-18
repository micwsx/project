package com.research.java8.optional;

public class OptionalFeature {

    public static void main(String[] args) {

        String carInsuranceName = getCarInsuranceName(new Person());
        System.out.println(carInsuranceName);

    }

    private static String getCarInsuranceNameNull_safe(Person person) {
        if (person == null) return "Unknown";
        Car car = person.getCar();
        if (car == null) return "Unknown";
        Insurance insurance = car.getInsurance();
        if (insurance == null) return "Unknown";
        return insurance.getName();
    }

    private static String getCarInsuranceNameWithDefensiveChecking(Person person) {
        if (person != null) {
            Car car = person.getCar();
            if (car != null) {
                Insurance insurance = car.getInsurance();
                if (insurance != null)
                    return insurance.getName();
            }
        }
        return "Unknown";
    }

    private static String getCarInsuranceName(Person person) {
        return person.getCar().getInsurance().getName();
    }


}

class Person {
    private Car car;

    public Car getCar() {
        return car;
    }
}

class Car {
    private Insurance insurance;

    public Insurance getInsurance() {
        return insurance;
    }
}

class Insurance {
    private String name;

    public String getName() {
        return name;
    }
}
