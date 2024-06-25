package org.example.components.models.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommentDto {
    private int id;
    private Message message;
    private Long postTime;
    private boolean edited;
    private String author;
}