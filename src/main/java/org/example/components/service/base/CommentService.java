package org.example.components.service.base;

import org.example.components.models.Comment;
import org.example.components.models.Message;

import java.util.List;

public interface CommentService {
    Comment postComment(Message message);

    List<Comment> getComment();

    Comment patchComment(int id, Message message);

    void deleteComment(int id);
}
