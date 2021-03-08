package com.research.http;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * @author Michael
 * @create 9/14/2020 1:29 PM
 */
public class HTTPRequestClass {
    public static void main(String[] args) {
        send("Hello World");
    }

    private static void send(String message) {
        System.out.println("发送消息：" + message);
        Socket socket = new Socket();
        try {
            socket.connect(new InetSocketAddress(8888));
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            bufferedWriter.write(message);
            bufferedWriter.flush();
            bufferedWriter.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
