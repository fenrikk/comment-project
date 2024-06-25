package org.example.components.service.base;

import org.example.components.models.dto.comment.CommentDto;
import org.example.components.models.dto.comment.Message;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CommentService {
    CommentDto postComment(Message message, String username);

    List<CommentDto> getComments();

    CommentDto patchComment(int id, Message message, String username);

    ResponseEntity<String> deleteComment(int id, String username);
}
