package org.example.components.repository;

import org.example.components.models.Comment;
import org.example.components.models.mapper.CommentMapper;
import org.example.components.repository.base.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Component
public class DatabaseCommentRepository implements CommentRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DatabaseCommentRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Comment insertComment(Comment newComment) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO Comment(message, posttime, edited) VALUES (?, ?, ?)",
                    new String[]{"id"}
            );
            ps.setString(1, newComment.getMessage().getMessageString());
            ps.setLong(2, newComment.getPostTime());
            ps.setBoolean(3, newComment.isEdited());
            return ps;
        }, keyHolder);
        newComment.setId(keyHolder.getKey().intValue());
        return newComment;
    }

    @Override
    public List<Comment> getComments() {
        return jdbcTemplate.query("SELECT * FROM Comment", new CommentMapper());
    }

    @Override
    public Comment updateComment(Comment commentToUpdate) {
        jdbcTemplate.update(
                "UPDATE Comment SET message =?, posttime =?, edited =? WHERE id =?",
                commentToUpdate.getMessage().getMessageString(),
                commentToUpdate.getPostTime(),
                commentToUpdate.isEdited(),
                commentToUpdate.getId()
        );
        return commentToUpdate;
    }

    @Override
    public void removeComment(Comment commentToDelete) {
        jdbcTemplate.update(
                "DELETE FROM Comment WHERE id =?",
                commentToDelete.getId()
        );
    }
}
