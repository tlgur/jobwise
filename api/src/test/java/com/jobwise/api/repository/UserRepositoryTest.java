package com.jobwise.api.repository;

import com.jobwise.api.domain.User;
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
public class UserRepositoryTest extends RepositoryTest {
    @Autowired
    private EntityManager em;
    @Autowired
    private UserRepository userRepository;

    private Logger log = LoggerFactory.getLogger(UserRepositoryTest.class);
    private User sampleUser;

    @BeforeEach
    public void beforeEach(){
        String sampleID = "SampleID";
        String samplePW = "SamplePW";
        String sampleName = "SampleName";
        String sampleNickName = "SampleNickName";
        sampleUser = User.newUser(sampleID, samplePW, sampleName, sampleNickName);
    }

    /**
     * input :
     * expect result :
     */
    @Test
    public void newUser_생성및조회() throws Exception{
        //given

        //mocking

        //when
        User savedUser = userRepository.save(sampleUser);
        Long id = savedUser.getId();
        em.flush();
        em.clear();

        //then
        Optional<User> findUser = userRepository.findById(id);
        assertThat(findUser).isPresent();
        assertThat(findUser).get().usingRecursiveComparison().isEqualTo(sampleUser);

        log.debug("findUser.get().getId() : {}", findUser.get().getId());
        log.debug("findUser.get().getId() : {}", findUser.get().getUsername());
        log.debug("findUser.get().getId() : {}", findUser.get().getPassword());
        log.debug("findUser.get().getId() : {}", findUser.get().getNickname());
        log.debug("findUser.get().getRealName() : {}", findUser.get().getRealName());
    }
}
