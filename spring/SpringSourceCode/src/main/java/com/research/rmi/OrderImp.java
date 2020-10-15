package com.research.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * @author Michael
 * @create 9/14/2020 2:19 PM
 */
public class OrderImp extends UnicastRemoteObject implements IOrder {

    public OrderImp() throws RemoteException {
    }

    @Override
    public String pay(int orderNum) {
        return "订单"+orderNum+"已支付";
    }
}
