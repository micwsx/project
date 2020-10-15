package com.research.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;

/**
 * @author Michael
 * @create 9/14/2020 1:35 PM
 */
public class ReceiveDemo {
    public static void main(String[] args) {
        try {
            DatagramSocket datagramSocket = new DatagramSocket(10005);
            byte[] bytes = new byte[datagramSocket.getReceiveBufferSize()];
            DatagramPacket datagramPacket = new DatagramPacket(bytes, bytes.length);
            while (true) {
                datagramSocket.receive(datagramPacket);
                byte[] data = datagramPacket.getData();
                String message=new String(data, 0, datagramPacket.getLength());
                System.out.println("接收消息" + message);
                if (message.equals("q")) {
                    break;
                }
            }
            datagramSocket.close();
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
