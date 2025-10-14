package com.jobwise.api.domain.content;

import com.jobwise.api.domain.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@DiscriminatorValue("COMMENT")
public class Comment extends Content {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "root_post_id")
    private Post rootPost;

    private String body;

    private Comment(User writer, String body, Content parent) {
        super(writer);
        this.body = body;
        parent.addComment(this);
    }

    public static Comment newComment(User writer, String body, Content parent, Post rootPost) {
        Comment comment = new Comment(writer, body, parent);
        writer.writeNewComment(comment);
        comment.rootPost = rootPost;
        return comment;
    }
}