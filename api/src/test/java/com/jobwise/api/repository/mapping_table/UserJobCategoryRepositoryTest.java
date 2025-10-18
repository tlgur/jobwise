package com.jobwise.api.repository.mapping_table;

import com.jobwise.api.domain.JobCategory;
import com.jobwise.api.domain.User;
import com.jobwise.api.domain.mapping_table.UserJobCategory;
import com.jobwise.api.repository.RepositoryTest;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class UserJobCategoryRepositoryTest extends RepositoryTest {
    @Autowired
    private EntityManager em;
    @Autowired
    private UserJobCategoryRepository userJobCategoryRepository;

    private Logger log = LoggerFactory.getLogger(UserJobCategoryRepositoryTest.class);
    private User sampleUser;
    private JobCategory sampleJobCategory;

    @BeforeEach
    public void beforeEach(){
        String sampleID = "SampleID";
        String samplePW = "SamplePW";
        String sampleName = "SampleName";
        String sampleNickName = "SampleNickName";
        sampleUser = User.newUser(sampleID, samplePW, sampleName, sampleNickName);
        sampleJobCategory = JobCategory.newJobCategory("sampleJob");
    }

    /**
     * input :
     * expect result :
     */
    @Test
    public void matchUserAndJob_생성및조회() throws Exception{
        //given
        em.persist(sampleUser);
        em.persist(sampleJobCategory);
        em.flush();
        em.clear();

        //mocking

        //when
        UserJobCategory userJobCategory = UserJobCategory.matchUserAndJob(sampleUser, sampleJobCategory);
        userJobCategoryRepository.save(userJobCategory);
        Long id = userJobCategory.getId();
        em.flush();
        em.clear();

        //then
        Optional<UserJobCategory> findUserJobCategory = userJobCategoryRepository.findById(id);
        assertThat(findUserJobCategory).isPresent();
        log.debug("findUserJobCategory.get().getId() : {}", findUserJobCategory.get().getId());

        //연관된 User
        User findUser = em.find(User.class, findUserJobCategory.get().getUser().getId());
        assertThat(findUser)
                .isNotNull()
                .usingRecursiveComparison()
                .comparingOnlyFields("username", "password", "realName", "nickname")
                .isEqualTo(sampleUser);
        assertThat(findUser.getJobCategories()).contains(findUserJobCategory.get());

//        //연관된 Job
        JobCategory findJobCategory = em.find(JobCategory.class, findUserJobCategory.get().getJobCategory().getId());
        assertThat(findJobCategory)
                .isNotNull()
                .usingRecursiveComparison()
                .comparingOnlyFields("name")
                .isEqualTo(sampleJobCategory);
        assertThat(findJobCategory.getUserJobCategories()).contains(findUserJobCategory.get());
    }
}