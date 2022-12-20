package com.gjz.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gjz.pojo.Tag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.jpa.repository.JpaRepository;

//mybatis
//@Mapper
//public interface TagDao extends BaseMapper<Tag> {
//    @Select("select * from t_tag where name=#{name}")
//    Type findByName(String name);
//}




//jpa

public interface TagDao extends JpaRepository<Tag, Long> {
    Tag findByName(String name);
}
