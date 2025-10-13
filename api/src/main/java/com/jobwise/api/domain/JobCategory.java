package com.jobwise.api.domain;

import com.jobwise.api.domain.mapping_table.PostJobCategory;
import com.jobwise.api.domain.mapping_table.UserJobCategory;
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
public class JobCategory {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "job_category_id")
    private Long id;

    private String name;
//
//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "jobCategory", cascade = CascadeType.ALL)
//    private List<PostJobCategory> postJobCategory = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "jobCategory", cascade = CascadeType.ALL)
    private List<UserJobCategory> userJobCategory = new ArrayList<>();

}
