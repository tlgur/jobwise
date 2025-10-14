package com.jobwise.api.repository.content;

import com.jobwise.api.domain.content.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
