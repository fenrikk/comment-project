package org.example.components.repository.base;

import org.example.components.models.Comment;

import java.util.List;

public interface CommentRepository {

    Comment insertComment(Comment newComment);

    List<Comment> getComments();

    Comment updateComment(Comment commentToUpdate);

    void removeComment(Comment commentToDelete);
}