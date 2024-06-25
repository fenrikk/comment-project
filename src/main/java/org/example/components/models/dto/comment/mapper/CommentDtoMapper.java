package org.example.components.models.dto.comment.mapper;

import org.example.components.models.dto.comment.CommentDto;
import org.example.components.models.dto.comment.Message;
import org.example.components.models.entity.Comment;

public class CommentDtoMapper {
    public static CommentDto mapToDto(Comment comment) {
        return new CommentDto(
                comment.getId(),
                new Message(comment.getMessage()),
                comment.getPostTime(),
                comment.isEdited(),
                comment.getAuthor().getUsername()
        );
    }
}
