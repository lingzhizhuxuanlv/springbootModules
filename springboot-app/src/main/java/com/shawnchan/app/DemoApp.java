package com.shawnchan.app;

import com.shawnchan.core.model.Result;
import com.shawnchan.core.model.User;
import com.shawnchan.core.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "演示接口",tags = "演示接口")
@RestController
@RequestMapping(value = "/app")
public class DemoApp {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @ApiOperation(value = "演示接口")
    @GetMapping(value = "/demo")
    public Object demo() {
        try{
            User user =  userService.selectByPrimaryKey(1);
            redisTemplate.opsForValue().set("1",user.getName());
            String name = (String)redisTemplate.opsForValue().get("1");
            return Result.buildOK("获取成功",name);
        }catch (Exception e){
            return Result.buildERROR("获取失败");
        }
    }
}
