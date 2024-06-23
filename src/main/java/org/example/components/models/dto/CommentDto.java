package org.example.components.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommentDto {
    private int id;
    private Message message;
    private Long postTime;
    private boolean edited;
}