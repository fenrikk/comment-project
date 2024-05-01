package org.example.components;

import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
public class Comments {
    private final List<CommentRestController.Comment> comments = new LinkedList<>();

    private Comments() {}

    public void addComment(CommentRestController.Comment comment) {
        comments.add(comment);
    }

    public List<CommentRestController.Comment> getComments() {
        return comments;
    }
}
