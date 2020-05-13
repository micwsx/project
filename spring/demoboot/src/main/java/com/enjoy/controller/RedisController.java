package com.enjoy.controller;

import com.enjoy.model.User;
import com.enjoy.service.dao.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * 验证使用redis缓存
 */
@Controller
@RequestMapping("/redis")
public class RedisController {

    @Autowired
    private UserService userService;

    @ResponseBody
    @RequestMapping("/select/{id}")
    public User select(@PathVariable(name = "id") Integer userid) {
        User user = userService.selectById(userid);
        return user;
    }

    @ResponseBody
    @RequestMapping("/selectAll")
    public List<User> selectALL() {
        List<User> list = userService.selectAll();
        return list;
    }
}
