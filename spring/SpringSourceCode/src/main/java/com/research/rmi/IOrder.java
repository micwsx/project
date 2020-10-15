package com.research.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @author Michael
 * @create 9/14/2020 2:19 PM
 */
public interface IOrder extends Remote {

    String pay(int orderNum)throws RemoteException;;
}
