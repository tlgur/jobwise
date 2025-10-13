package com.jobwise.api.domain;

import com.jobwise.api.domain.content.Comment;
import com.jobwise.api.domain.mapping_table.UserJobCategory;
import com.jobwise.api.domain.content.Post;
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
public class User {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String username;
    private String password;

    private String realName;
    private String nickname;

    /*
    TBD : 좋아요/취소/스크랩 기능
     */

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "writer", cascade = CascadeType.ALL)
    private final List<Post> writtenPost = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "writer", cascade = CascadeType.ALL)
    private final List<Comment> writtenComments = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL)
    private final List<UserJobCategory> jobCategories = new ArrayList<>();

    public void writeNewPost(Post post) {
        writtenPost.add(post);
    }

    public void writeNewComment(Comment comment) {
        writtenComments.add(comment);
    }

    private User(String username, String password, String realName, String nickname) {
        this.username = username;
        this.password = password;
        this.realName = realName;
        this.nickname = nickname;
    }

    public static User newUser(String username, String password, String realName, String nickname, JobCategory... matchingJobs) {
        User user = new User(username, password, realName, nickname);
        Arrays.stream(matchingJobs)
                .map(job -> UserJobCategory.matchUserAndJob(user, job))
                .forEach(user.jobCategories::add);
        return user;
    }
}
