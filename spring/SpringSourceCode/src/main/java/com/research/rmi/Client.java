package com.research.rmi;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @author Michael
 * @create 9/14/2020 2:27 PM
 */
public class Client {
    public static void main(String[] args) {

        try {
            IOrder order = (IOrder) Naming.lookup("rmi://localhost:6666/order");
            String result = order.pay(18624);
            System.out.println(result);

        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }
}
