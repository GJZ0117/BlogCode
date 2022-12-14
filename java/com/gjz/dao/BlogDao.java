package com.gjz.dao;

import com.gjz.pojo.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface BlogDao extends JpaRepository<Blog, Long>, JpaSpecificationExecutor<Blog> {

    @Query("select b from Blog b where b.recommend=true")
    List<Blog> findTop(Pageable pageable);

    @Query("select b from Blog b where b.title like ?1 or b.content like ?1")
    Page<Blog> findByQuery(String query, Pageable pageable);

    @Transactional
    @Modifying
    @Query("update Blog b set b.views = b.views+1 where b.id=?1")
    int updateViews(Long id);

    @Query("select function('date_format', b.createTime, '%Y') as year from Blog b group by function('date_format', b.createTime, '%Y') order by function('date_format', b.createTime, '%Y') desc")
    List<String> findGroupYear();

    @Query("select b from Blog b where function('date_format', b.createTime, '%Y') = ?1")
    List<Blog> findByYear(String year);

    @Transactional
    @Modifying
    @Query(value = "delete from t_blog_tags where blogs_id=?1", nativeQuery = true)
    void deleteBlogTags(Long blogId);

    @Transactional
    @Modifying
    @Query(value = "delete from t_comment where blog_id=?1", nativeQuery = true)
    void deleteBlogComments(Long blogId);
}
