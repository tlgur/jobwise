package com.jobwise.api.repository.mapping_table;

import com.jobwise.api.domain.JobCategory;
import com.jobwise.api.domain.Post;
import com.jobwise.api.domain.User;
import com.jobwise.api.domain.mapping_table.PostJobCategory;
import com.jobwise.api.repository.RepositoryTest;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class PostJobCategoryRepositoryTest extends RepositoryTest {
    @Autowired
    private PostJobCategoryRepository postJobCategoryRepository;
    @Autowired
    private EntityManager em;

    private Post samplePost;
    private JobCategory sampleJobCategory;

    @BeforeEach
    public void beforeEach(){

        User writer = User.newUser(
                "writerID"
                , "samplePw"
                , "writerName"
                , "writerNickname"
        );
        JobCategory job = JobCategory.newJobCategory("post Job");
        samplePost = Post.newPost(writer, "title", "body", job);
        sampleJobCategory = JobCategory.newJobCategory("sample Job");

        em.persist(job);
        em.persist(writer);
        em.persist(samplePost);
        em.persist(sampleJobCategory);
        em.flush();
        em.clear();
    }

    /**
     * input :
     * expect result :
     */
    @Test
    public void tagPostAndJob_생성및조회() throws Exception{
        //given

        //mocking

        //when
        PostJobCategory postJobCategory = PostJobCategory.tagPostAndJob(samplePost, sampleJobCategory);
        postJobCategoryRepository.save(postJobCategory);
        Long id = postJobCategory.getId();
        em.flush();
        em.clear();

        //then
        Optional<PostJobCategory> findPostJobCategory = postJobCategoryRepository.findById(id);
        assertThat(findPostJobCategory).isPresent();

        Post findPost = findPostJobCategory.get().getPost();
        assertThat(findPost)
                .usingRecursiveComparison()
                .comparingOnlyFields("title", "body")
                .isEqualTo(samplePost);
        assertThat(findPost.getTaggedJobs())
                .contains(findPostJobCategory.get());

        assertThat(findPostJobCategory.get().getJobCategory())
                .usingRecursiveComparison()
                .comparingOnlyFields("name")
                .isEqualTo(sampleJobCategory);

    }
}