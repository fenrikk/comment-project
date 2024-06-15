package org.example.components.models;

public class Comment {
    private int id;
    private Message message;
    private Long postTime;
    private boolean edited;

    public Comment(Message message, Long postTime, boolean edited) {
        this.message = message;
        this.postTime = postTime;
        this.edited = edited;
    }

    public Comment() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public Long getPostTime() {
        return postTime;
    }

    public void setPostTime(Long postTime) {
        this.postTime = postTime;
    }

    public boolean isEdited() {
        return edited;
    }

    public void setEdited(boolean edited) {
        this.edited = edited;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", message=" + message +
                ", postTime=" + postTime +
                ", edited=" + edited +
                '}';
    }
}