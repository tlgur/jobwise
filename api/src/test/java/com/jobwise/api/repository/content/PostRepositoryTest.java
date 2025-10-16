package com.jobwise.api.repository.content;

import com.jobwise.api.domain.JobCategory;
import com.jobwise.api.domain.User;
import com.jobwise.api.domain.content.Post;
import com.jobwise.api.repository.RepositoryTest;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class PostRepositoryTest extends RepositoryTest {

    @Autowired
    private EntityManager em;
    @Autowired
    private PostRepository postRepository;

    /**
     * input :
     * expect result :
     */
    @Test
    public void POST저장_() throws Exception{
        //given
        JobCategory testJob1 = JobCategory.newJobCategory("testJob1");
        JobCategory testJob2 = JobCategory.newJobCategory("testJob2");
        JobCategory testJob3 = JobCategory.newJobCategory("testJob3");
        JobCategory userJob = JobCategory.newJobCategory("testJob4");
        User user = User.newUser("testID", "testPw", "testUser", "testy"
        );
        String title = "test Title";
        String body = "test Body";

        //mocking
        em.persist(userJob);
        em.persist(user);
        em.persist(testJob1);
        em.persist(testJob2);
        em.persist(testJob3);

        //when
        Post post = Post.newPost(user, title, body
                , testJob1, testJob2, testJob3);
        em.persist(post);
        Long id = post.getId();
        em.flush();
        em.clear();

        //then
        Optional<Post> savedPost = postRepository.findById(id);
        assertThat(savedPost).isNotEmpty();
        assertThat(savedPost.get()).usingRecursiveComparison().isEqualTo(post);
    }
}