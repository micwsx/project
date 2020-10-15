package com.research.rpc;

import com.enjoy.rmi.IOrder;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * @author Michael
 * @create 9/14/2020 2:48 PM
 */
public class Client {

    public static <T> T createProxy(Class<?> clazz) {
        InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1",6666);
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class<?>[]{clazz}, new ProxyInvocationHandler(clazz,inetSocketAddress));
    }

    public static class ProxyInvocationHandler implements InvocationHandler {

        private InetSocketAddress inetSocketAddress;
        private Class<?> targetClazz;

        public ProxyInvocationHandler(Class<?> targetClazz,InetSocketAddress inetSocketAddress) {
            this.targetClazz=targetClazz;
            this.inetSocketAddress = inetSocketAddress;
        }

        @Override
        public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
            // TCP发送消息
            Socket socket = new Socket();
            ObjectOutputStream objectOutputStream =null;
            ObjectInputStream objectInputStream =null;
            try{
                socket.connect(inetSocketAddress);
                objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                objectOutputStream.writeObject(targetClazz);//接口类
                objectOutputStream.writeObject(method.getName());//方法名
                objectOutputStream.writeObject(method.getParameterTypes());//参数类型
                objectOutputStream.writeObject(objects);//参数值
                objectInputStream = new ObjectInputStream(socket.getInputStream());
                Object object = objectInputStream.readObject();
                objectOutputStream.flush();
                return object;
            }finally {
                objectOutputStream.close();
                objectInputStream.close();
                socket.close();
            }
        }
    }


    public static void main(String[] args) {

        Product proxy = (Product) createProxy(Product.class);
        String result = proxy.getPrice("Pudding");
        System.out.println(result);

    }
}
