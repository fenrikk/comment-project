package org.example.components.models.dto.mapper;

import org.example.components.models.Comment;
import org.example.components.models.dto.CommentDto;
import org.example.components.models.dto.Message;

public class CommentDtoMapper {
    public static CommentDto mapToDto(Comment comment) {
        return new CommentDto(
                comment.getId(),
                new Message(comment.getMessage()),
                comment.getPostTime(),
                comment.isEdited()
        );
    }
}
