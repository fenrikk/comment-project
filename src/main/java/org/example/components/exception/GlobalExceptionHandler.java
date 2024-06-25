package org.example.components.exception;

import org.example.components.exception.controller.auth.IncorrectLoginException;
import org.example.components.exception.controller.auth.UsernameAlreadyTakenException;
import org.example.components.exception.controller.comment.AuthorizedUserNotFoundException;
import org.example.components.exception.controller.comment.ChangeNotAllowedException;
import org.example.components.exception.controller.comment.CommentNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((org.springframework.validation.FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler
    public ResponseEntity<String> handleCommentNotFound(CommentNotFoundException ex) {
        return new ResponseEntity<>(ex.toString(), HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public ResponseEntity<String> handleAuthorizedUserNotFound(AuthorizedUserNotFoundException ex) {
        return new ResponseEntity<>(ex.toString(), HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler
    public ResponseEntity<String> handleChangeNotAllowedException(ChangeNotAllowedException ex) {
        return new ResponseEntity<>(ex.toString(), HttpStatus.FORBIDDEN);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public ResponseEntity<String> handleIncorrectLoginException(IncorrectLoginException ex) {
        return new ResponseEntity<>(ex.toString(), HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public ResponseEntity<String> handleUsernameAlreadyTakenException(UsernameAlreadyTakenException ex) {
        return new ResponseEntity<>(ex.toString(), HttpStatus.BAD_REQUEST);
    }
}