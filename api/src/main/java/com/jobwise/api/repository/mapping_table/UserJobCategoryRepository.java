package com.jobwise.api.repository.mapping_table;

import com.jobwise.api.domain.mapping_table.UserJobCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserJobCategoryRepository extends JpaRepository<UserJobCategory, Long> {
}
