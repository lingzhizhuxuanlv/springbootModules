package com.lingzhizhuxuanlv.springbootModules.web;

import com.lingzhizhuxuanlv.springbootModules.core.model.Result;
import com.lingzhizhuxuanlv.springbootModules.core.model.User;
import com.lingzhizhuxuanlv.springbootModules.core.service.UserService;
import com.lingzhizhuxuanlv.springbootModules.web.aop.WebLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/web")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @RequestMapping(value = "/toLogin",method = RequestMethod.GET)
    public String toLogin() {
        return "login";
    }

    @RequestMapping(value = "/toUpload",method = RequestMethod.GET)
    public String toUpload() {
        return "upload";
    }

    @RequestMapping(value = "/demo", method = RequestMethod.GET)
    public String toDemo(){
        return "demo";
    }

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String toIndex(){
        return "index";
    }

    @RequestMapping(value = "/mysqlTest", method = RequestMethod.GET)
    @ResponseBody
    @WebLog(methodName = "mysql测试")
    public Object mysqlTest(){
        try{
            User user =  userService.selectByPrimaryKey(1);
            return Result.buildOK("获取成功",user);
        }catch(Exception e){
            return Result.buildERROR("获取失败");
        }
    }

    @RequestMapping(value = "/redisTest", method = RequestMethod.GET)
    @ResponseBody
    @WebLog(methodName = "redis测试")
    public Object redisTest(){
        try{
            User user =  userService.selectByPrimaryKey(2);
            redisTemplate.opsForValue().set("2",user.getName());
            String name = (String)redisTemplate.opsForValue().get("2");
            return Result.buildOK("获取成功",name);
        }catch(Exception e){
            return Result.buildERROR("获取失败");
        }
    }

}
