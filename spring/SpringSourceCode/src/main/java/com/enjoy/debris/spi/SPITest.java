package com.enjoy.debris.spi;

import java.util.Iterator;
import java.util.ServiceLoader;

public class SPITest {

    // META-INF/services/接口全路径文件名
    public static void main(String[] args) {
        ServiceLoader<SPIService> serviceLoader= ServiceLoader.load(SPIService.class);
        Iterator iterator=serviceLoader.iterator();
        while (iterator.hasNext()){
            SPIService spiService=(SPIService)iterator.next();
            spiService.print();
        }
    }
}
