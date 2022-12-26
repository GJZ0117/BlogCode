package com.gjz.service;

import com.gjz.pojo.Comment;

import java.util.List;

public interface CommentService {

    List<Comment> listCommentByBlogId(Long blogId);

    Comment saveComment(Comment comment);

    void flushCommentsByBlogId(Long blogId);

    void deleteComments(Long commentId);

}
