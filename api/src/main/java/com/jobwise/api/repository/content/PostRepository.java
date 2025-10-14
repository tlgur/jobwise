package com.jobwise.api.repository.content;

import com.jobwise.api.domain.content.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
