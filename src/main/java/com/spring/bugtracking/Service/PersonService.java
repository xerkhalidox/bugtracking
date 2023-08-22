package com.spring.bugtracking.Service;

import com.spring.bugtracking.Dto.PersonDto;
import com.spring.bugtracking.Entity.Person;
import com.spring.bugtracking.Repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PersonService {
    @Autowired
    private PersonRepository personRepo;

    public PersonDto getByEmail(String email) {
        Person person = personRepo.getPersonByEmail(email).orElse(null);
        return mapPersonToDto(person);
    }

    public PersonDto add(PersonDto personDto) {
        if (personDto != null && personDto.getEmail() != null) {
            Person existingPerson = personRepo.getPersonByEmail(personDto.getEmail()).orElse(null);
            if (existingPerson != null) return mapPersonToDto(existingPerson);
            Person person = personRepo.save(mapDtoToPerson(personDto));
            return mapPersonToDto(person);
        } else {
            return null;
        }
    }

    @Transactional
    public void delete(String email) {
        personRepo.deletePersonByEmail(email);
    }

    private Person mapDtoToPerson(PersonDto personDto) {
        Person person = new Person();
        person.setName(personDto.getName());
        person.setEmail(personDto.getEmail());
        return person;
    }

    private PersonDto mapPersonToDto(Person person) {
        if (person == null) return null;
        PersonDto personDto = new PersonDto();
        personDto.setId(person.getId());
        personDto.setName(person.getName());
        personDto.setEmail(person.getEmail());
        return personDto;
    }
}
