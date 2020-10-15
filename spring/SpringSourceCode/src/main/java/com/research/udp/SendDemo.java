package com.research.udp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.*;

/**
 * @author Michael
 * @create 9/14/2020 1:46 PM
 */
public class SendDemo {
    public static void main(String[] args) throws Exception {
        DatagramSocket datagramSocket=new DatagramSocket();
        BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(System.in));
        String s=null;
        while ((s=bufferedReader.readLine())!=null){

            byte[] data=s.getBytes();
            DatagramPacket datagramPacket=new DatagramPacket(data,data.length,InetAddress.getLocalHost(),10005);
            datagramSocket.send(datagramPacket);
            if (s.equals("q")){
                break;
            }
        }
        bufferedReader.close();
        datagramSocket.close();
    }
}
