package com.jobwise.api.domain;

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
public class User {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String username;
    private String password;

    private String realName;
    private String nickname;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "writer", cascade = CascadeType.ALL)
    private final List<Post> writtenPost = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "writer", cascade = CascadeType.ALL)
    private final List<Comment> writtenComments = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", orphanRemoval = true)
    private final List<UserJobCategory> jobCategories = new ArrayList<>();

    private User(String username, String password, String realName, String nickname) {
        this.username = username;
        this.password = password;
        this.realName = realName;
        this.nickname = nickname;
    }

    public static User newUser(String username, String password, String realName, String nickname) {
        User user = new User(username, password, realName, nickname);
        return user;
    }

    public void writeNewPost(Post post) {
        writtenPost.add(post);
    }

    public void writeNewComment(Comment comment) {
        writtenComments.add(comment);
    }

    public void addNewJob(UserJobCategory matchingJob) {
        jobCategories.add(matchingJob);
    }
}
