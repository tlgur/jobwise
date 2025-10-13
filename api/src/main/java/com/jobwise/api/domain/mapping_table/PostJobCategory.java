package com.jobwise.api.domain.mapping_table;

import com.jobwise.api.domain.JobCategory;
import com.jobwise.api.domain.content.Post;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class PostJobCategory {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "post_job_category_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_category_id")
    private JobCategory jobCategory;

    private PostJobCategory(Post post, JobCategory jobCategory) {
        this.post = post;
        this.jobCategory = jobCategory;
    }

    public static PostJobCategory tagPostAndJob(Post post, JobCategory taggingJob) {
        return new PostJobCategory(post, taggingJob);
    }
}
