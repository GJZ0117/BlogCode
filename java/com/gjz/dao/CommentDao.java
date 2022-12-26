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

    @Query("select c from Comment c where c.parentComment.id=?1")
    List<Comment> findCommentsByParentComment(Long parentCommentId);

    @Transactional
    @Modifying
    @Query(value = "update t_comment set parent_comment_id=null where blog_id=?1", nativeQuery = true)
    void flushCommentsByBlogId(Long blogId);

    @Transactional
    @Modifying
    @Query(value = "update t_comment set parent_comment_id=null where id in ?1", nativeQuery = true)
//    @Query("update Comment c set c.parentComment.id=null where c.id in ?1")
    void flushParentCommentByCommentId(Long[] commentIds);

    @Transactional
    @Modifying
    @Query(value = "delete from t_comment where id in ?1", nativeQuery = true)
    void deleteCommentsById(Long[] commentIds);
}
