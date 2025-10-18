package com.jobwise.api.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Comment {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    private String body;
    private int depth = 0;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "writer_id")
    private User writer;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Comment parent;

    @OneToMany(mappedBy = "parent")
    private final List<Comment> comments = new ArrayList<>();

    private void addComment(Comment comment) {
        comment.depth = this.depth + 1;
        comment.parent = this;
        this.comments.add(comment);
    }

    private Comment(User writer, String body, Post post) {
        this.writer = writer;
        this.body = body;
        this.post = post;
        this.depth = 0;
        this.parent = null;
    }

    public static Comment newCommentToPost(User writer, String body, Post post) {
        Comment comment = new Comment(writer, body, post);
        writer.writeNewComment(comment);
        return comment;
    }

    public static Comment newCommentToComment(User writer, String body, Comment parent) {
        Comment comment = new Comment(writer, body, parent.post);
        parent.addComment(comment);
        writer.writeNewComment(comment);
        return comment;
    }
}
