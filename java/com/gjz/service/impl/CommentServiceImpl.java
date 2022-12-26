package com.gjz.service.impl;

import com.gjz.dao.CommentDao;
import com.gjz.pojo.Comment;
import com.gjz.service.CommentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentDao commentDao;

    @Transactional
    @Override
    public List<Comment> listCommentByBlogId(Long blogId) {
        Sort sort = Sort.by(Sort.Direction.ASC, "createTime");
        List<Comment> comments = commentDao.findByBlogIdAndParentCommentNull(blogId, sort);
        return eachComment(comments);
    }

    @Override
    public Comment saveComment(Comment comment) {
        Long parentCommentId = comment.getParentComment().getId();
        if (parentCommentId != -1) {
            comment.setParentComment(commentDao.findById(parentCommentId).orElse(null));
        } else {
            comment.setParentComment(null);
        }
        comment.setCreateTime(new Date());
        return commentDao.save(comment);
    }

    @Override
    public void flushCommentsByBlogId(Long blogId) {
        commentDao.flushCommentsByBlogId(blogId);
    }

    @Override
    public void deleteComments(Long commentId) {
        List<Long> deleteCommentId = new ArrayList<>();
        Queue<Long> queue = new LinkedList<>();
        deleteCommentId.add(commentId);
        queue.add(commentId);
        while (!queue.isEmpty()) {
            Long parentCommentId = queue.poll();
            List<Comment> childrenComments = commentDao.findCommentsByParentComment(parentCommentId);
            for (Comment c : childrenComments) {
                deleteCommentId.add(c.getId());
                queue.add(c.getId());
            }
        }
        Long[] ids = deleteCommentId.toArray(new Long[deleteCommentId.size()]);
        commentDao.flushParentCommentByCommentId(ids);
        commentDao.deleteCommentsById(ids);
    }

    private List<Comment> eachComment(List<Comment> comments) {
        List<Comment> commentsView = new ArrayList<>();
        for (Comment comment : comments) {
            Comment c = new Comment();
            BeanUtils.copyProperties(comment, c);
            commentsView.add(c);
        }
        combineChildren(commentsView);
        return commentsView;
    }

    private void combineChildren(List<Comment> comments) {

        for (Comment comment : comments) {
            List<Comment> replys1 = comment.getReplyComment();
            for (Comment reply1 : replys1) {
                recursively(reply1);
            }
            comment.setReplyComment(tempReplys);
            tempReplys = new ArrayList<>();
        }
    }

    private List<Comment> tempReplys = new ArrayList<>();

    private void recursively(Comment comment) {
        tempReplys.add(comment);
        if (comment.getReplyComment().size() > 0) {
            List<Comment> replys = comment.getReplyComment();
            for (Comment reply : replys) {
                tempReplys.add(reply);
                if (reply.getReplyComment().size() > 0) {
                    recursively(reply);
                }
            }
        }
    }
}
