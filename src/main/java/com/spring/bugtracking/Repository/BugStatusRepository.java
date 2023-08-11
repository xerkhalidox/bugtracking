package com.spring.bugtracking.Repository;

import com.spring.bugtracking.Entity.BugStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BugStatusRepository extends JpaRepository<BugStatus, Integer> {
    Optional<BugStatus> getBugStatusByStatusName(String name);
}
