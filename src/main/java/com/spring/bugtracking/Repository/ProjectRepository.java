package com.spring.bugtracking.Repository;

import com.spring.bugtracking.Entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}
