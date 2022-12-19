package com.gjz.service.impl;

import com.gjz.dao.UserDao;
import com.gjz.pojo.User;
import com.gjz.service.UserService;
import com.gjz.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User checkUser(String username, String password) {
        User  user = userDao.findByUsernameAndPassword(username, password);
        return user;
    }
}
