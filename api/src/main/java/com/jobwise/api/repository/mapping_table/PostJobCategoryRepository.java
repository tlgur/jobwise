package com.jobwise.api.repository.mapping_table;

import com.jobwise.api.domain.mapping_table.PostJobCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostJobCategoryRepository extends JpaRepository<PostJobCategory, Long> {
}
