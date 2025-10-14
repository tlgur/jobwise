package com.jobwise.api.domain.content;

import com.jobwise.api.domain.User;
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
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn()
public abstract class Content {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "content_id")
    private Long id;

    private int depth = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Content parent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer_id")
    private User writer;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Content> comments = new ArrayList<>();

    protected Content(User writer) {
        this.parent = this;
        this.writer = writer;
        this.depth = 0;
    }

    protected void addComment(Content comment) {
        comments.add(comment);
        comment.parent = this;
        comment.depth = this.depth + 1;
    }
}
