package com.enjoy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.net.URL;
import java.net.URLClassLoader;

@Controller
@RequestMapping("/user")
public class UserController {

    @RequestMapping("/info")
    public @ResponseBody String info() {
        URLClassLoader systemClassLoader = (URLClassLoader) ClassLoader.getSystemClassLoader();
        URL[] urLs = systemClassLoader.getURLs();
        for (URL url : urLs) {
            System.out.println(url);
        }
        System.out.println("--------------------------");
        System.out.println("sssssss");
        //获取类加载器。
        System.out.println(UserController.class.getClassLoader());
        System.out.println(Object.class.getClassLoader());
        System.out.println(java.lang.Object.class.getClassLoader());

        System.out.println(String.class.getClassLoader());
        return "Hello";
    }

    @RequestMapping("/home")
    public ModelAndView print(){
        ModelAndView modelAndView=new ModelAndView("cors");
        modelAndView.addObject("name", "Michael");
        return modelAndView;
    }



}
