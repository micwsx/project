package com.research.java8.optional;

import java.util.Optional;

public class RedefiningPerson {
    public static void main(String[] args) {


        Optional<Person2> person2=Optional.empty();

        String result=getInsuranceName(Optional.of(new Person2()));
    }

    public static String getInsuranceName(Optional<Person2> person2) {
        return person2
                .flatMap(Person2::getCar2)
                .flatMap(Car2::getInsurance2)
                .map(Insurance2::getName)
                .orElse("Unknown");


//        Optional<Car2> car2 = Optional.empty();
//        Car2 instance = new Car2();
//        Optional<Car2> car21 = Optional.of(instance);
//        Optional<Car2> optCar=Optional.ofNullable(instance);

    }
}

class Person2 {
    private Optional<Car2> car2;

    public Optional<Car2> getCar2() {
        return car2;
    }
}

class Car2 {

    private Optional<Insurance2> insurance2;

    public Optional<Insurance2> getInsurance2() {
        return insurance2;
    }
}

class Insurance2 {
    private String name;

    public String getName() {
        return name;
    }
}