package com.spring.bugtracking.Repository;

import com.spring.bugtracking.Dto.PersonDto;
import com.spring.bugtracking.Entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Long> {
    Optional<Person> getPersonByEmail(String email);

    void deletePersonByEmail(String email);
}
