package org.example.components.service.base;

import org.example.components.models.dto.CommentDto;
import org.example.components.models.dto.Message;

import java.util.List;

public interface CommentService {
    CommentDto postComment(Message message);

    List<CommentDto> getComment();

    CommentDto patchComment(int id, Message message);

    void deleteComment(int id);
}
