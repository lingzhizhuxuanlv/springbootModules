package com.lingzhizhuxuanlv.springbootModules.core.model;

import lombok.Data;

@Data
public class Result {

    private Integer status;
    private String msg;
    private Object data;

    private Result(Integer status, String msg, Object data){
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    private Result(Integer status, String msg){
        this.status = status;
        this.msg = msg;
    }

    public static Result buildOK(String msg){
        return new Result(200, msg);
    }

    public static Result buildOK(String msg, Object data){
        return new Result(200, msg, data);
    }

    public static Result buildERROR(String msg){
        return new Result(500, msg);
    }

}
