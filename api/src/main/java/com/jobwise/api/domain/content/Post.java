package com.jobwise.api.domain.content;

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
    private String title;
    private String body;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<PostJobCategory> taggingJobs = new ArrayList<>();

}