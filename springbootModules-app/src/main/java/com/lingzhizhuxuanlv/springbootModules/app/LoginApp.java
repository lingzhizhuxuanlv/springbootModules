package com.lingzhizhuxuanlv.springbootModules.app;

import com.lingzhizhuxuanlv.springbootModules.app.util.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Api(value = "登陆接口",tags = "登陆接口")
@RestController
@RequestMapping(value = "/app")
public class LoginApp {

    @ApiOperation(value = "登陆接口")
    @PostMapping(value = "/login")
    @ApiImplicitParams({
            @ApiImplicitParam(name="username", value="用户名", paramType="query", dataType="string", example="admin", required=true),
            @ApiImplicitParam(name="password", value="密码", paramType="query", dataType="string", example="123456", required=true)
    })
    public Object login(String username, String password) {
        if ("admin".equals(username) && "123456".equals(password)) {
            String token = JwtUtil.generateToken(username,password);
            Map<String,String> map = new HashMap<>(1);
            map.put("token",token);
            return map;
        }
        return "null";
    }

}
