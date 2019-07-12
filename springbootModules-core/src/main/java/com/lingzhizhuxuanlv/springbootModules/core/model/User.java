package com.lingzhizhuxuanlv.springbootModules.core.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class User implements Serializable {

    private Integer id;

    private String name;

    private String password;

}
