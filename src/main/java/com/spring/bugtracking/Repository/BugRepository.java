package com.spring.bugtracking.Repository;

import com.spring.bugtracking.Entity.Bug;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BugRepository extends JpaRepository<Bug, Long> {
}
