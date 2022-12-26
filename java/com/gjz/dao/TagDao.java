package com.gjz.dao;

import com.gjz.pojo.Tag;
import com.gjz.pojo.Type;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

//mybatis
//@Mapper
//public interface TagDao extends BaseMapper<Tag> {
//    @Select("select * from t_tag where name=#{name}")
//    Type findByName(String name);
//}




//jpa

public interface TagDao extends JpaRepository<Tag, Long> {
    Tag findByName(String name);

    @Query("select t from Tag t")
    List<Tag> findTop(Pageable pageable);
}
