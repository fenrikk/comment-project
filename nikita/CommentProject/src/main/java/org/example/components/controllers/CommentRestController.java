package org.example.components.controllers;

import org.example.components.model.CommentsSingleton;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class CommentRestController {

    public static class CommentsResponse {
        private List<Comment> comments;

        public List<Comment> getComments() {
            return comments;
        }

        public void setComments(List<Comment> comments) {
            this.comments = comments;
        }
    }

    public static class Comment {
        private String comment;

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        @Override
        public String toString() {
            return comment;
        }
    }

    @RequestMapping(
            value = "/getComments",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public CommentsResponse getComments() {
        CommentsResponse result = new CommentsResponse();
        result.setComments(CommentsSingleton.getInstance().getComments());
        return result;
    }

    @RequestMapping(value = "/addComment", method = RequestMethod.POST)
    public Comment addComment(@RequestBody Comment comment) {
        CommentsSingleton.getInstance().addComment(comment);
        return comment;
    }
}
