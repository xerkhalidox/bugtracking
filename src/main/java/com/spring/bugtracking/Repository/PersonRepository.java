package com.spring.bugtracking.Repository;

import com.spring.bugtracking.Entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
}
