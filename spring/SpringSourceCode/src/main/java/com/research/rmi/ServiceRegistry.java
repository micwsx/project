package com.research.rmi;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

/**
 * @author Michael
 * @create 9/14/2020 2:22 PM
 */
public class ServiceRegistry {
    public static void main(String[] args) throws Exception {
        IOrder order=new OrderImp();
        LocateRegistry.createRegistry(6666);
        Naming.bind("rmi://localhost:6666/order",order);
        System.out.println("服务已注册");
    }
}
