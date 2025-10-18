package com.jobwise.api.domain;

import com.jobwise.api.domain.mapping_table.PostJobCategory;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Post {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "post_id")
    private Long id;

    private String title;
    private String body;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer_id")
    private User writer;

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<PostJobCategory> taggedJobs = new ArrayList<>();

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Comment> comments = new ArrayList<>();

    private Post(User writer, String title, String body){
        this.writer = writer;
        this.title = title;
        this.body = body;
    }

    public static Post newPost(User writer, String title, String body, JobCategory... taggedJobs) {
        Post post = new Post(writer, title, body);
        writer.writeNewPost(post);
        Arrays.stream(taggedJobs)
                .map(job -> PostJobCategory.tagPostAndJob(post, job))
                .forEach(post.taggedJobs::add);
        return post;
    }

    public void addComment(Comment comment) {
        comments.add(comment);
    }
}
