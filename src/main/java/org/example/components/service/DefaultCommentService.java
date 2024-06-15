package org.example.components.service;

import org.example.components.models.Comment;
import org.example.components.models.Message;
import org.example.components.repository.DatabaseCommentRepository;
import org.example.components.repository.base.CommentRepository;
import org.example.components.service.base.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DefaultCommentService implements CommentService {

    private final CommentRepository commentRepository;

    @Autowired
    public DefaultCommentService(DatabaseCommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public Comment postComment(Message message) {
        Comment commentToInsert = new Comment(message, System.currentTimeMillis(), false);
        return commentRepository.insertComment(commentToInsert);
    }

    @Override
    public List<Comment> getComment() {
        return commentRepository.getComments();
    }

    @Override
    public Comment patchComment(int id, Message message) {
        Comment commentToPatch = new Comment(message, System.currentTimeMillis(), true);
        commentToPatch.setId(id);
        return commentRepository.updateComment(commentToPatch);
    }

    @Override
    public void deleteComment(int id) {
        Comment commentToDelete = new Comment();
        commentToDelete.setId(id);
        commentRepository.removeComment(commentToDelete);
    }
}
