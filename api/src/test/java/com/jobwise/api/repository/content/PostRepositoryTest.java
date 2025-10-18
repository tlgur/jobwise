package com.jobwise.api.repository.content;

import com.jobwise.api.domain.JobCategory;
import com.jobwise.api.domain.User;
import com.jobwise.api.domain.mapping_table.PostJobCategory;
import com.jobwise.api.domain.mapping_table.UserJobCategory;
import com.jobwise.api.repository.PostRepository;
import com.jobwise.api.repository.RepositoryTest;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class PostRepositoryTest extends RepositoryTest {

    @Autowired
    private EntityManager em;
    @Autowired
    private PostRepository postRepository;

    private JobCategory sampleJobCategory;
    private User sampleUser;

    @BeforeEach
    public void beforeEach() {
        sampleJobCategory = JobCategory.newJobCategory("sampleJob");
        sampleUser = User.newUser(
                "sampleID"
                , "samplePW"
                , "sampleName"
                , "sampleNickName"
        );

        JobCategory sampleUserJob = JobCategory.newJobCategory("sample User Job");
        UserJobCategory sampleUserJobCategory = UserJobCategory.matchUserAndJob(sampleUser, sampleUserJob);
    }

    /**
     * input :
     * expect result :
     */
    @Test
    public void newPost_생성및조회() throws Exception {
        //given
        String title = "test Title";
        String body = "test Body";

        //mocking
        em.persist(sampleUser);
        em.persist(sampleJobCategory);
        em.flush();
        em.clear();

        //when
        Post post = Post.newPost(sampleUser, title, body, sampleJobCategory);
        postRepository.save(post);
        Long id = post.getId();
        em.flush();
        em.clear();

        //then
        Optional<Post> findPost = postRepository.findById(id);
        assertThat(findPost).isPresent();
        assertThat(findPost.get().getId()).isNotNull();
        assertThat(findPost).get()
                .usingRecursiveComparison()
                .comparingOnlyFields("title", "body")
                .isEqualTo(post);

        //Content Tree 검증
        assertThat(findPost.get().getDepth())
                .isEqualTo(0);
        assertThat(findPost.get().getParent()).isNull();

        //연관관계 검증 - tagging Job
        Stream<JobCategory> findJobCategory = findPost.get()
                .getTaggingJobs()
                .stream()
                .map(PostJobCategory::getJobCategory);
        assertThat(findJobCategory)
                .extracting(JobCategory::getName).contains(sampleJobCategory.getName());

        //연관관계 검증 - 작성자
        User writer = findPost.get().getWriter();
        assertThat(writer)
                .usingRecursiveComparison()
                .comparingOnlyFields("username", "password", "realName", "nickname")
                .isEqualTo(sampleUser);


    }
}