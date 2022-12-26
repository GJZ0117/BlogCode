package com.gjz.dao;

import com.gjz.pojo.Type;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.List;

//mybatis
//@Mapper
//public interface TypeDao extends BaseMapper<Type> {
//    @Select("select * from t_type where name=#{name}")
//    Type findByName(String name);
//}




//jpa

public interface TypeDao extends JpaRepository<Type, Long> {
    Type findByName(String name);

    @Query("select t from Type t")
    List<Type> findTop(Pageable pageable);
}