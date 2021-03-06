package com.enjoy.controller;

import com.enjoy.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 操作mongo
 */
@Controller
@RequestMapping("/mongo")
public class MongoController {

    @Autowired
    private MongoTemplate mongoTemplate;

    @ResponseBody
    @RequestMapping("/add/{id}")
    public User addUser(@PathVariable(value = "id", required = true) Integer id) {
        User user=new User(id, "Michael", true, "First person in mongo.");
        mongoTemplate.insert(user);
        return user;
    }

}
