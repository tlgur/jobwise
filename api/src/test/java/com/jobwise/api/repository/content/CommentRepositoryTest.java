package com.jobwise.api.repository.content;

import com.jobwise.api.domain.JobCategory;
import com.jobwise.api.domain.User;
import com.jobwise.api.domain.content.Content;
import com.jobwise.api.repository.RepositoryTest;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class CommentRepositoryTest extends RepositoryTest {
    @Autowired
    private EntityManager em;
    @Autowired
    private CommentRepository commentRepository;

    private User sampleUser;
    private Post samplePost;

    @BeforeEach
    public void beforeEach(){
        sampleUser = User.newUser(
                "sampleID"
                , "samplePW"
                , "sampleName"
                , "sampleNickName"
        );
        User writer = User.newUser(
                "writerID"
                , "samplePw"
                , "writerName"
                , "writerNickname"
        );
        JobCategory job = JobCategory.newJobCategory("post Job");
        samplePost = Post.newPost(writer, "title", "body", job);

        em.persist(job);
        em.persist(writer);
        em.persist(sampleUser);
        em.persist(samplePost);
        em.flush();
        em.clear();
    }

    /**
     * input :
     * expect result :
     */
    @Test
    public void newComment_Post에댓글() throws Exception{
        //given
        String body = "comment body";

        //mocking

        //when
        Comment comment = Comment.newComment(sampleUser, body, samplePost, samplePost);
        commentRepository.save(comment);
        Long id = comment.getId();
        em.flush();
        em.clear();

        //then
        Optional<Comment> findComment = commentRepository.findById(id);
        assertThat(findComment).isPresent();
        assertThat(findComment).get()
                .extracting(Comment::getBody)
                .isEqualTo(body);

        //Content Tree 검증
        assertThat(findComment.get().getDepth()).isEqualTo(samplePost.getDepth() + 1);
        Content parentContent = findComment.get().getParent();

        assertThat(findComment.get().getRootPost())
                .usingRecursiveComparison()
                .isEqualTo(parentContent);
        assertThat(parentContent)
                .usingRecursiveComparison()
                .comparingOnlyFields("title", "body")
                .isEqualTo(samplePost);
//        assertThat(parentContent.getComments())
//                .extracting()
//                .contains()


        //연관관계 검증 - writer
        User writer = findComment.get().getWriter();
        assertThat(writer)
                .usingRecursiveComparison()
                .comparingOnlyFields("username", "password", "realName", "nickname")
                .isEqualTo(sampleUser);

    }
}