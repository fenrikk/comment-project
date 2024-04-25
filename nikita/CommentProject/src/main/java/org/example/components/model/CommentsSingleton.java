package org.example.components.model;

import org.example.components.controllers.CommentRestController;

import java.util.LinkedList;
import java.util.List;

public class CommentsSingleton {
    private static CommentsSingleton instance;
    private final List<CommentRestController.Comment> comments = new LinkedList<>();

    private CommentsSingleton() {
    }

    public static synchronized CommentsSingleton getInstance() {
        if (instance == null) {
            instance = new CommentsSingleton();
        }
        return instance;
    }

    public void addComment(CommentRestController.Comment comment) {
        comments.add(comment);
    }

    public List<CommentRestController.Comment> getComments() {
        return comments;
    }
}
