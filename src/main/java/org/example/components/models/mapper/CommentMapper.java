package org.example.components.models.mapper;

import org.example.components.models.Comment;
import org.example.components.models.Message;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CommentMapper implements RowMapper<Comment> {
    @Override
    public Comment mapRow(ResultSet resultSet, int i) throws SQLException {
        Comment comment = new Comment();
        Message message = new Message();
        comment.setId(resultSet.getInt("id"));
        message.setMessageString(resultSet.getString("message"));
        comment.setMessage(message);
        comment.setPostTime(resultSet.getLong("postTime"));
        comment.setEdited(resultSet.getBoolean("edited"));
        return comment;
    }
}
