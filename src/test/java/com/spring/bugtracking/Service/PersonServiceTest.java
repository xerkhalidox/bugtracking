package com.spring.bugtracking.Service;

import com.spring.bugtracking.Dto.PersonDto;
import com.spring.bugtracking.Entity.Person;
import com.spring.bugtracking.Repository.PersonRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PersonServiceTest {
    @Mock
    private PersonRepository personRepo;

    @InjectMocks
    private PersonService personService;

    private static PodamFactory podamFactory;

    @BeforeAll
    static void init() {
        podamFactory = new PodamFactoryImpl();
    }

    @Test
    void PersonService_addPerson_ReturnsTheAddedPerson() {
        Person toBeAddedPerson = podamFactory.manufacturePojo(Person.class);
        PersonDto personDto = podamFactory.manufacturePojo(PersonDto.class);
        personDto.setName(toBeAddedPerson.getName());
        personDto.setEmail(toBeAddedPerson.getEmail());

        when(personRepo.getPersonByEmail(Mockito.anyString())).thenReturn(Optional.empty());
        when(personRepo.save(Mockito.any(Person.class))).thenReturn(toBeAddedPerson);
        PersonDto savedPersonDto = personService.add(personDto);

        Assertions.assertNotNull(savedPersonDto);
        Assertions.assertEquals(savedPersonDto.getName(), personDto.getName());
        Assertions.assertEquals(savedPersonDto.getEmail(), personDto.getEmail());
    }

    @Test
    void PersonService_addExistingPerson_ReturnsTheExistingPerson() {
        Person toBeAddedPerson1 = podamFactory.manufacturePojo(Person.class);
        PersonDto personDto1 = podamFactory.manufacturePojo(PersonDto.class);
        personDto1.setName(toBeAddedPerson1.getName());
        personDto1.setEmail(toBeAddedPerson1.getEmail());

        Person toBeAddedPerson2 = podamFactory.manufacturePojo(Person.class);
        toBeAddedPerson2.setName(toBeAddedPerson1.getName());
        toBeAddedPerson2.setEmail(toBeAddedPerson1.getEmail());
        PersonDto personDto2 = podamFactory.manufacturePojo(PersonDto.class);
        personDto2.setName(personDto1.getName());
        personDto2.setEmail(personDto1.getEmail());

        // add the person
        when(personRepo.getPersonByEmail(Mockito.anyString())).thenReturn(Optional.empty());
        when(personRepo.save(Mockito.any(Person.class))).thenReturn(toBeAddedPerson1);
        PersonDto savedPersonDto = personService.add(personDto1);

        // add the same person again
        when(personRepo.getPersonByEmail(toBeAddedPerson1.getEmail())).thenReturn(Optional.of(toBeAddedPerson2));
        PersonDto existingPersonDto = personService.add(personDto2);

        Assertions.assertNotNull(savedPersonDto);
        Assertions.assertNotNull(existingPersonDto);
        Assertions.assertEquals(savedPersonDto.getName(), existingPersonDto.getName());
        Assertions.assertEquals(savedPersonDto.getEmail(), existingPersonDto.getEmail());
    }

    @Test
    void PersonService_GetPersonByEmail_ReturnsTheCorrectPerson() {
        Person toBeAddedPerson = podamFactory.manufacturePojo(Person.class);
        PersonDto personDto = podamFactory.manufacturePojo(PersonDto.class);
        personDto.setName(toBeAddedPerson.getName());
        personDto.setEmail(toBeAddedPerson.getEmail());

        // add the person first
        when(personRepo.getPersonByEmail(Mockito.anyString())).thenReturn(Optional.empty());
        when(personRepo.save(Mockito.any(Person.class))).thenReturn(toBeAddedPerson);
        PersonDto savedPersonDto = personService.add(personDto);

        // try to get the person by email
        when(personRepo.getPersonByEmail(Mockito.anyString())).thenReturn(Optional.of(toBeAddedPerson));
        PersonDto personByEmail = personService.add(personDto);

        Assertions.assertNotNull(savedPersonDto);
        Assertions.assertNotNull(personByEmail);
        Assertions.assertEquals(personByEmail.getName(), savedPersonDto.getName());
        Assertions.assertEquals(personByEmail.getEmail(), savedPersonDto.getEmail());
    }
}