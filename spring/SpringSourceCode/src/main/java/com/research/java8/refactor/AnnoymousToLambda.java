package com.research.java8.refactor;

/**
 * 匿名类转化成lamabda问题
 */
public class AnnoymousToLambda {

    public static void main(String[] args) {
//        存在ambiguous
//        doSomething(()->{
//            System.out.println("");
//        });
//        转换
        doSomething((Task)()->{
            System.out.println("");
        });

    }

    public static void doSomething(Runnable runnable) {
        runnable.run();
    }

    public static void doSomething(Task t) {
        t.execute();
    }

    interface Task {
        void execute();
    }

    public static void refactor() {
        int a = 10;
        Runnable r1 = () -> {
//            int a = 2; 编译错误
            System.out.println("");
        };

        Runnable r2 = new Runnable() {
            @Override
            public void run() {
                int a = 2;
                System.out.println(a);
            }
        };
    }
}
