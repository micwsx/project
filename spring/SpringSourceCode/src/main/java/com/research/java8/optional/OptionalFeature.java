package com.research.java8.optional;

import java.util.Optional;
import java.util.OptionalInt;

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

    private static Optional<Insurance> tes(Optional<Person> optPerson, Optional<Car> optCar) {



        return optPerson.flatMap(p -> optCar.map(c -> new Insurance()));
    }




    private static String getCarInsuranceNameOptional(Optional<Person1> person) {

        Optional<Optional<Car1>> car12 = Optional.ofNullable(new Person1()).map(Person1::getCar);

        Optional<Optional<Car1>> car1 = person.map(Person1::getCar);
        Optional<Car1> car11 = person.flatMap(Person1::getCar);
        car11.ifPresent(System.out::println);

        return person.flatMap(Person1::getCar)
                .flatMap(Car1::getInsurance)
                .map(Insurance1::getName)
                .orElse("Unknown");
    }


}


class Person1 {
    private Optional<Car1> car;

    public Optional<Car1> getCar() {
        return car;
    }
}

class Car1 {
    private Optional<Insurance1> insurance;

    public Optional<Insurance1> getInsurance() {
        return insurance;
    }
}

class Insurance1 {
    private String name;

    public String getName() {
        return name;
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
