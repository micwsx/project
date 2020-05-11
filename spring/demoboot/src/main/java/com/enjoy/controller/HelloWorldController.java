package com.enjoy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.net.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Optional;

@Controller
public class HelloWorldController {

    @GetMapping("/")
    public ModelAndView info() throws Exception {
        String  localhostAddress= InetAddress.getLocalHost().getHostAddress().toString();
        ModelAndView modelAndView=new ModelAndView("info");
        Optional<Inet4Address> ipAddress=getLocalIpAddress();
        modelAndView.addObject("localhostAddress",localhostAddress);
        modelAndView.addObject("ipAddress",ipAddress.isPresent()?ipAddress.get().getHostAddress():"null");
        return modelAndView;
    }

    /**
     * 内网ＩＰ
     * @return
     * @throws SocketException
     */
    private List<Inet4Address> getLocalIp4AddressFromNetworkInterface() throws SocketException {
        List<Inet4Address> addresses=new ArrayList<>(1);
        Enumeration enumeration= NetworkInterface.getNetworkInterfaces();
        if (enumeration !=null) {
            while (enumeration.hasMoreElements()) {
                NetworkInterface networkInterface = (NetworkInterface) enumeration.nextElement();
                boolean isValidInterface = !networkInterface.isLoopback()
                        && !networkInterface.isPointToPoint()
                        && networkInterface.isUp()
                        && !networkInterface.isVirtual()
                        && (networkInterface.getName().startsWith("eth") || networkInterface.getName().startsWith("ens"));
                if (isValidInterface) {
                    Enumeration e=networkInterface.getInetAddresses();
                    while (e.hasMoreElements()){
                        InetAddress inetAddress=(InetAddress)e.nextElement();
                        boolean isValidAddress=inetAddress instanceof  Inet4Address
                                && inetAddress.isSiteLocalAddress()
                                &&!inetAddress.isLoopbackAddress();
                        if (isValidAddress){
                            addresses.add((Inet4Address)inetAddress);
                        }
                    }
                }
            }
        }
        return  addresses;
    }

    /**
     * 外网ＩＰ
     * @return
     * @throws SocketException
     */
    private Optional<Inet4Address> getIpBySocket() throws  SocketException{
        try {
            final DatagramSocket socket=new DatagramSocket();
            socket.connect(InetAddress.getByName("8.8.8.8"),1002);
            if (socket.getLocalAddress() instanceof Inet4Address){
                return Optional.of((Inet4Address)socket.getLocalAddress());
            }
        }catch (UnknownHostException e){
            throw  new RuntimeException(e);
        }
        return Optional.empty();
    }

    private Optional<Inet4Address> getLocalIpAddress() throws SocketException {
        final List<Inet4Address> ipByNi=getLocalIp4AddressFromNetworkInterface();
        if (ipByNi.isEmpty()||ipByNi.size()>1){
            final  Optional<Inet4Address> ipBysocketOpt=getIpBySocket();
            if (ipBysocketOpt.isPresent()){
                return  ipBysocketOpt;
            }else{
                return  ipByNi.isEmpty()?Optional.empty(): Optional.of(ipByNi.get(0));
            }
        }
        return Optional.of(ipByNi.get(0));
    }

}
