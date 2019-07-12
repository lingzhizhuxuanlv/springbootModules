package com.lingzhizhuxuanlv.springbootModules.api;

import com.lingzhizhuxuanlv.springbootModules.core.model.Result;
import com.lingzhizhuxuanlv.springbootModules.core.model.User;
import com.lingzhizhuxuanlv.springbootModules.core.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Api(value = "演示接口",tags = "演示接口")
@RestController
@RequestMapping(value = "/api")
public class DemoApi {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @ApiOperation(value = "演示接口")
    @GetMapping(value = "/demo")
    @ApiImplicitParams({
            @ApiImplicitParam(name="id", value="id", paramType="query", dataType="int", required=true),
            @ApiImplicitParam(name="info", value="信息", paramType="query", dataType="string", required=true)
    })
    public Object demo(Integer id,String info){
        try{
            List<Object> list = new ArrayList<>();
            list.add(id);
            list.add(info);
            User user =  userService.selectByPrimaryKey(1);
            redisTemplate.opsForValue().set("1",user.getName());
            String name = (String)redisTemplate.opsForValue().get("1");
            list.add(name);
            return Result.buildOK("获取成功",list);
        }catch (Exception e){
            return Result.buildERROR("获取失败");
        }
    }

}
