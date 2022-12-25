package com.gjz.dao;

import com.gjz.pojo.Comment;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface CommentDao extends JpaRepository<Comment, Long> {

    List<Comment> findByBlogIdAndParentCommentNull(Long blogId, Sort sort);

    @Transactional
    @Modifying
    @Query(value = "update t_comment set parent_comment_id=null where blog_id=?1", nativeQuery = true)
    void flushCommentsByBlogId(Long blogId);
}
