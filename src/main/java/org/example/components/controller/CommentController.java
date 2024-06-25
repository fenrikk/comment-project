package org.example.components.controller;

import lombok.AllArgsConstructor;
import org.example.components.models.dto.comment.CommentDto;
import org.example.components.models.dto.comment.Message;
import org.example.components.service.base.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/comment")
    public CommentDto addComment(
            @RequestBody
            Message message,
            Authentication authentication
    ) {
        return commentService.postComment(message, authentication.getName());
    }

    @GetMapping("/feed")
    public List<CommentDto> getComments() {
        return commentService.getComments();
    }

    @PatchMapping("/comment/{id}")
    public CommentDto patchComment(
            @PathVariable("id")
            int id,
            @RequestBody
            Message message,
            Authentication authentication
    ) {
        return commentService.patchComment(id, message, authentication.getName());
    }

    @DeleteMapping("/comment/{id}")
    public ResponseEntity<String> deleteComment(
            @PathVariable("id")
            int id,
            Authentication authentication
    ) {
        return commentService.deleteComment(id, authentication.getName());
    }
}