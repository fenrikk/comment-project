package org.example.components;

import java.util.LinkedList;
import java.util.List;

public class Comments {
    private static Comments instance;
    private final List<CommentRestController.Comment> comments = new LinkedList<>();

    private Comments() {
    }

    public static synchronized Comments getInstance() {
        if (instance == null) {
            instance = new Comments();
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
