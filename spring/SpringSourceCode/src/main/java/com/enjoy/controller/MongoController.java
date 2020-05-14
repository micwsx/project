package com.enjoy.controller;

import com.enjoy.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/mongo")
public class MongoController {

    @Autowired
    private MongoTemplate mongoTemplate;

    @ResponseBody
    @RequestMapping("/add/{id}")
    public User addUser(@PathVariable(value = "id") Integer id){
        User user=new User(id, "Spring", false, "User in Spring project");
        mongoTemplate.insert(user);
        return user;
    }


}
