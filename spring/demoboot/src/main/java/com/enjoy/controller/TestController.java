package com.enjoy.controller;

import com.enjoy.model.User;
import com.enjoy.service.dao.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/test")
public class TestController {

    @Autowired
    private UserService userService;

    @CrossOrigin(origins = {"*"},
            allowedHeaders = "x-requested-with",
            allowCredentials = "true",
            exposedHeaders = "CrossOrigin-Method",
            maxAge = 3600,
            methods = {RequestMethod.GET})
    @RequestMapping("/info")
    public @ResponseBody String info() throws Exception {
        return "{\"name\":\"Michael\",\"msg\":\"Test Here!!\"}";
    }


    @RequestMapping(value = "/jsonp")
    public @ResponseBody String jsonp() throws Exception {
        return "callbackFuc({\"name\":\"Michael\",\"msg\":\"Test Here!!\"});";
    }

    @ResponseBody
    @RequestMapping(value = "/users")
    public List<User> getUsers(){
       return userService.selectAll();
    }

    @ResponseBody
    @RequestMapping(value = "/update/{userid}")
    public int updateUser(@PathVariable(value = "userid") Integer id){
        User user=userService.selectById(id);
        user.setRemark("Test update user in slave Db.");
        return userService.update(user);
    }


//    @ResponseBody
//    @RequestMapping(value = "/update/{userid}")
//    public int updateUser(@RequestParam(value = "userid",required = false) Integer id){
//        User user=userService.selectById(id);
//        user.setRemark("Test update user in slave Db.");
//        return userService.update(user);
//    }
}
