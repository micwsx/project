package com.research.rpc;


import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Michael
 * @create 9/14/2020 2:32 PM
 * 服务注册
 */
public class ServerRegister {

    public static Map<Class, String> serviceMap = new HashMap<>();

    public void register(Class clazz, String qualifyName) {
        serviceMap.put(clazz, qualifyName);
    }

    public static Map<Class, String> getServiceMap() {
        return serviceMap;
    }

    public void start() {
        // 接收用户请求数据
        try {
            ServerSocket serverSocket = new ServerSocket();
            serverSocket.bind(new InetSocketAddress(6666));
            while (true) {
                Socket socket = serverSocket.accept();
                try {
                    ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
                    Class clazz = (Class) objectInputStream.readObject();
                    String methodName = (String) objectInputStream.readObject();
                    Class<?>[] types = (Class<?>[]) objectInputStream.readObject();
                    Object[] objects = (Object[]) objectInputStream.readObject();
                    String qualifiedName = serviceMap.get(clazz);
                    Object originatedObj = Class.forName(qualifiedName).newInstance();
                    System.out.println(clazz.getName() + "-" + originatedObj);

                    Method method = clazz.getMethod(methodName, types);
                    Object result = method.invoke(originatedObj, objects);

                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                    objectOutputStream.writeObject(result);
                    objectOutputStream.flush();

                    objectInputStream.close();
                    objectOutputStream.close();
                    socket.close();

                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }


    public static void main(String[] args) {
        ServerRegister serverRegister = new ServerRegister();
        serverRegister.register(Product.class, "com.enjoy.rpc.ProductImp");
        System.out.println("服务已注册:");
        System.out.println(serviceMap);
        serverRegister.start();
    }

}
