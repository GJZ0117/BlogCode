package com.gjz.service;

import com.gjz.pojo.User;

public interface UserService {
    User checkUser(String username, String password);
}
