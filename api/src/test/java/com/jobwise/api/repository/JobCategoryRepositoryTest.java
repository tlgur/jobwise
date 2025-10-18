package com.jobwise.api.repository;

import com.jobwise.api.domain.JobCategory;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class JobCategoryRepositoryTest extends RepositoryTest {
    @Autowired
    private EntityManager em;
    @Autowired
    private JobCategoryRepository jobCategoryRepository;

    private Logger log = LoggerFactory.getLogger(JobCategoryRepository.class);
    private JobCategory sampleJobCategory;

    @BeforeEach
    public void beforeEach(){
        sampleJobCategory = JobCategory.newJobCategory("sampleJob");
    }

    /**
     * input :
     * expect result :
     */
    @Test
    public void newJobCategory_생성및조회() throws Exception{
        //given
        
        //mocking
        
        //when
        jobCategoryRepository.save(sampleJobCategory);
        Long id = sampleJobCategory.getId();
        em.flush();
        em.clear();

        //then
        Optional<JobCategory> findJobCategory = jobCategoryRepository.findById(id);
        assertThat(findJobCategory).isPresent();
        assertThat(findJobCategory).get().usingRecursiveComparison().isEqualTo(sampleJobCategory);
        log.debug("findJobCategory.get().getId() : {}", findJobCategory.get().getId());
        log.debug("findJobCategory.get().getId() : {}", findJobCategory.get().getName());
    }
}