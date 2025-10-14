package com.jobwise.api.domain.mapping_table;

import com.jobwise.api.domain.JobCategory;
import com.jobwise.api.domain.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class UserJobCategory {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "user_job_category_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_category_id")
    private JobCategory jobCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private UserJobCategory(User user, JobCategory jobCategory) {
        this.user = user;
        this.jobCategory = jobCategory;
    }

    public static UserJobCategory matchUserAndJob(User user, JobCategory taggingJob) {
        UserJobCategory userJobCategory = new UserJobCategory(user, taggingJob);
        taggingJob.matchUserAndJob(userJobCategory);
        return userJobCategory;
    }
}
