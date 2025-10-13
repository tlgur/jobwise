package com.jobwise.api.domain.content;

import com.jobwise.api.domain.JobCategory;
import com.jobwise.api.domain.User;
import com.jobwise.api.domain.mapping_table.PostJobCategory;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@DiscriminatorValue("POST")
public class Post extends Content {
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<PostJobCategory> taggingJobs = new ArrayList<>();
    private String title;
    @Lob
    private String body;

    private Post(User writer, String title, String body){
        super(writer);
        this.title = title;
        this.body = body;
    }

    public static Post newPost(User writer, String title, String body, JobCategory... taggingJobs) {
        Post post = new Post(writer, title, body);
        writer.writeNewPost(post);
        Arrays.stream(taggingJobs)
                .map(job -> PostJobCategory.tagPostAndJob(post, job))
                .forEach(post.taggingJobs::add);
        return post;
    }
}