package com.shawnchan;

import cn.hutool.http.HttpRequest;

public class MainDemo {
    public static void main(String[] args) {
        String a = "eyJhbGciOiJIUzI1NiJ9.eyJwYXNzd29yZCI6IjEyMzQ1NiIsImV4cCI6MTU0NjA3MTIyNCwiaWF0IjoxNTQ2MDcxMTM4LCJqdGkiOiJ0b2tlbklkIiwidXNlcm5hbWUiOiJhZG1pbiJ9.332nMGqqHPK1BHjhW39LX2LUFElxbiZej9BfzZ4LRGc";
        String resultP = HttpRequest
                .post("http://localhost:8080/springboot-app/app/login")
                .form("username","admin")
                .form("password","123456")
                .execute().body();
        String resultG = HttpRequest
                .get("http://localhost:8080/springboot-app/app/demo")
                .header("token",a)
                .execute().body();
        System.out.println(resultG);
    }
}

