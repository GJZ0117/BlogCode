package com.gjz.dao;

import com.gjz.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;

//mybatis

//@Mapper
//public interface UserDao extends BaseMapper<User> {
//    @Select("select * from t_user where username=#{username} and password=#{password}")
//    User findByUsernameAndPassword(String username, String password);
//}


//jpa

public interface UserDao extends JpaRepository<User, Long> {
        User findByUsernameAndPassword(String username, String password);
}