package com.research.interaction;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Michael
 * @create 9/14/2020 12:26 PM
 */
public class HTTPResponseClass {

    public static void main(String[] args) {
        ExecutorService executorService=Executors.newFixedThreadPool(100);

        try {
            ServerSocket serverSocket = new ServerSocket();
            serverSocket.bind(new InetSocketAddress(8888));
            System.out.println("TCP服务器已经启动，端口是8888");
            while (true) {
                // 阻塞等待用户请求
                Socket socket = serverSocket.accept();

                executorService.submit(() -> {
                    System.out.println(Thread.currentThread().getName() + "收到处理请求开始---------");
                    try {

                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        String s = null;
                        while ((s = bufferedReader.readLine()) != null && !"".equals(s)) {
                            System.out.println(s);
                        }

                        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                        //模拟了http的请求头信息
                        bufferedWriter.write("HTTP/1.1 200 OK \r\n");// 状态行
                        bufferedWriter.write("Content-Type: text/html;charset=utf-8 \r\n");//响应头
                        bufferedWriter.write("\r\n");//空行
                        //写一些html的体
                        bufferedWriter.write("<html><head><meta charset=\"UTF-8\"><title>http请求</title></head><body><h1>这是一个HTTP请求！</h1></body></html>");
                        bufferedWriter.flush();

                        bufferedReader.close();
                        bufferedWriter.close();
                        socket.close();

                        System.out.println(Thread.currentThread().getName() + "********处理请求结束********");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
