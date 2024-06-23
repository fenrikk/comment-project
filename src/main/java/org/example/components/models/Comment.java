package org.example.components.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Comment")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 5000)
    private String message;
    private Long postTime;
    private boolean edited;

    public Comment(String message, Long postTime, boolean edited) {
        this.message = message;
        this.postTime = postTime;
        this.edited = edited;
    }
}