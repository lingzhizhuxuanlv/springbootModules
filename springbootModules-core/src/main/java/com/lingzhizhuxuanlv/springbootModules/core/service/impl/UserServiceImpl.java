package com.lingzhizhuxuanlv.springbootModules.core.service.impl;

import com.lingzhizhuxuanlv.springbootModules.core.dao.UserMapper;
import com.lingzhizhuxuanlv.springbootModules.core.model.User;
import com.lingzhizhuxuanlv.springbootModules.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper dao;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return dao.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(User record) {
        return dao.insert(record);
    }

    @Override
    public int insertSelective(User record) {
        return dao.insertSelective(record);
    }

    @Override
    public User selectByPrimaryKey(Integer id) {
        return dao.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(User record) {
        return dao.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(User record) {
        return dao.updateByPrimaryKey(record);
    }

}
