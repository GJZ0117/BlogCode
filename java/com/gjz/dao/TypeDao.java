package com.gjz.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gjz.pojo.Type;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

//mybatis
//@Mapper
//public interface TypeDao extends BaseMapper<Type> {
//    @Select("select * from t_type where name=#{name}")
//    Type findByName(String name);
//}




//jpa

public interface TypeDao extends JpaRepository<Type, Long> {
    Type findByName(String name);
}
