package com.enjoy.controller;

import com.enjoy.model.User;
import com.enjoy.service.dao.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.List;

// @RestController渲染字符串返回调用者
@Controller
@RequestMapping("/api")
@Api(tags = "springboot相关接口")
public class ApiController {

    @Autowired
    private UserService userService;

    @Value("${application.message:default value michael}")
    private String applictionMessage = "";

    @ApiOperation("thymeleaf返回时间和消息接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "time", value = "时间", defaultValue = "system time"),
            @ApiImplicitParam(name = "msg", value = "消息", defaultValue = "define in properties")
    })
    @RequestMapping("/index")
    public ModelAndView apiIndex() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/api/index");
        modelAndView.addObject("time", new Date());
        modelAndView.addObject("message", applictionMessage);
        return modelAndView;
    }

    @ApiOperation("json查询所有用户接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "certerial", value = "ALL", defaultValue = "ALL", paramType = "path")
    })
    @RequestMapping("/users")
    public @ResponseBody
    List<User> apiUsers() {
        List<User> users = userService.selectAll();
        return users;
    }

    @ApiOperation("json测试接口")
    @RequestMapping("/test")
    public @ResponseBody
    String testDevTool() {
        return "OK";
    }
}

/*
@Api：用在类上，说明该类的作用。
@ApiOperation：注解来给API增加方法说明。
@ApiImplicitParams :用在方法上包含一组参数说明。
@ApiImplicitParam：用来注解来给方法入参增加说明。
        参数说明:
        name：参数名
        value：说明参数的意思
        defaultValue：参数的默认值
        dataType：参数类型
        required：参数是否必须传
        paramType：指定参数放在哪个地方
           header：请求参数放置于Request Header，使用@RequestHeader获取
           query：请求参数放置于请求地址，使用@RequestParam获取
           path：（用于restful接口）-->请求参数的获取：@PathVariable
           body：（不常用）请求体
           form（不常用）表单
*/



