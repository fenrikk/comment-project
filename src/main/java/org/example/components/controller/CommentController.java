package org.example.components.controller;

import org.example.components.models.Comment;
import org.example.components.models.Message;
import org.example.components.service.base.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/comment")
    public Comment addComment(@RequestBody Message message) {
        return commentService.postComment(message);
    }

    @GetMapping("/feed")
    public List<Comment> getComments() {
        return commentService.getComment();
    }

    @PatchMapping("/comment/{id}")
    public Comment patchComment(@PathVariable("id") int id, @RequestBody Message message) {
        return commentService.patchComment(id, message);
    }

    @DeleteMapping("/comment/{id}")
    public void deleteComment(@PathVariable("id") int id) {
        commentService.deleteComment(id);
    }
}
