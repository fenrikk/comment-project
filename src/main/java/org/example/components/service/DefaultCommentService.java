package org.example.components.service;

import lombok.AllArgsConstructor;
import org.example.components.exception.controller.comment.AuthorizedUserNotFoundException;
import org.example.components.exception.controller.comment.ChangeNotAllowedException;
import org.example.components.exception.controller.comment.CommentNotFoundException;
import org.example.components.models.dto.comment.CommentDto;
import org.example.components.models.dto.comment.Message;
import org.example.components.models.dto.comment.mapper.CommentDtoMapper;
import org.example.components.models.entity.Comment;
import org.example.components.models.entity.User;
import org.example.components.repository.CommentRepository;
import org.example.components.repository.UserRepository;
import org.example.components.service.base.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DefaultCommentService implements CommentService {

    private final CommentRepository commentRepository;
    private UserRepository userRepository;

    @Override
    public CommentDto postComment(Message message, String username) {
        User author = getUserFromRepository(username);
        Comment newComment = new Comment();
        newComment.setMessage(message.getMessageString());
        newComment.setPostTime(System.currentTimeMillis());
        newComment.setAuthor(author);
        commentRepository.save(newComment);
        return CommentDtoMapper.mapToDto(newComment);
    }

    @Transactional(readOnly = true)
    @Override
    public List<CommentDto> getComments() {
        return commentRepository.findAllWithAuthors().stream()
                .map(CommentDtoMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public CommentDto patchComment(int id, Message message, String username) {
        Comment commentToUpdate = getCommentFromRepository(id);
        checkIfChangingAllowed(commentToUpdate, username);
        commentToUpdate.setMessage(message.getMessageString());
        commentToUpdate.setPostTime(System.currentTimeMillis());
        commentToUpdate.setEdited(true);
        return CommentDtoMapper.mapToDto(commentRepository.save(commentToUpdate));
    }

    @Transactional
    @Override
    public ResponseEntity<String> deleteComment(int id, String username) {
        Comment commentToDelete = getCommentFromRepository(id);
        checkIfChangingAllowed(commentToDelete, username);
        commentRepository.deleteById(id);
        return ResponseEntity.ok("Deleted successful");
    }

    private void checkIfChangingAllowed(Comment comment, String username) {
        if (!comment.getAuthor().getUsername().equals(username)) {
            throw new ChangeNotAllowedException("Changing this comment not allowed.");
        }
    }

    private Comment getCommentFromRepository(int id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new CommentNotFoundException("Requested comment not found."));
    }

    private User getUserFromRepository(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new AuthorizedUserNotFoundException("Authorized user not found."));
    }
}
