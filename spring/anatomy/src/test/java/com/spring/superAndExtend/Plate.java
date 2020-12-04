package com.spring.superAndExtend;

import com.spring.App;

// T extends Fruit表示 T是Fruit子类。<? extend Fruit> 不能不能set,?类型不知道是不确定
// T super Fruit表示  T是Fruit超类。<? super Fruit> 不能get,只能Object返回对象
public class Plate<T extends Fruit> {
    private T item;

    public Plate(T item) {
        this.item = item;
    }

    public T getItem() {
        return item;
    }

    public void setItem(T item) {
        this.item = item;
    }

    public static void main(String[] args) {
//        Plate<Fruit> plate=new Plate<Apple>(new Apple());// 这样写报错
        Plate<Apple> plate = new Plate<Apple>(new Apple());
        //Plate<Fruit> plate = new Plate<Fruit>(new Apple());
        plate.setItem(new Apple());
        plate.getItem();
        System.out.println(plate);
    }
}
