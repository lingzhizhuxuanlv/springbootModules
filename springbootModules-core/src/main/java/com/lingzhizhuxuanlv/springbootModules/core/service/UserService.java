package com.lingzhizhuxuanlv.springbootModules.core.service;

import com.lingzhizhuxuanlv.springbootModules.core.model.User;

public interface UserService {

    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

}
