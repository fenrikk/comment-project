package org.example.components.service;

import org.example.components.models.Comment;
import org.example.components.models.dto.CommentDto;
import org.example.components.models.dto.Message;
import org.example.components.models.dto.mapper.CommentDtoMapper;
import org.example.components.repository.CommentRepository;
import org.example.components.service.base.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class DefaultCommentService implements CommentService {

    private final CommentRepository commentRepository;

    @Autowired
    public DefaultCommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public CommentDto postComment(Message message) {
        Comment newComment = new Comment();
        newComment.setMessage(message.getMessageString());
        newComment.setPostTime(System.currentTimeMillis());
        commentRepository.save(newComment);
        return CommentDtoMapper.mapToDto(newComment);
    }

    @Override
    public List<CommentDto> getComment() {
        List<CommentDto> commentDtoList = new LinkedList<>();
        commentRepository.findAll().forEach(comment ->
                commentDtoList.add(CommentDtoMapper.mapToDto(comment))
        );
        return commentDtoList;
    }

    @Override
    public CommentDto patchComment(int id, Message message) {
        Comment commentToUpdate = new Comment(message.getMessageString(), System.currentTimeMillis(), true);
        commentToUpdate.setId(id);
        return CommentDtoMapper.mapToDto(commentRepository.save(commentToUpdate));
    }

    @Override
    public void deleteComment(int id) {
        commentRepository.deleteById(id);
    }
}
