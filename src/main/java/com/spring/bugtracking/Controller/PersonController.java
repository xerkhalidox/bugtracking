package com.spring.bugtracking.Controller;

import com.spring.bugtracking.Dto.PersonDto;
import com.spring.bugtracking.Service.PersonService;
import com.spring.bugtracking.exception.ApiRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("person")
public class PersonController {
    @Autowired
    private PersonService personService;

    @GetMapping("/{email}")
    public ResponseEntity<PersonDto> getPerson(@PathVariable String  email) {
        try {
            PersonDto person = personService.getByEmail(email);
            HttpStatus status = person == null ? HttpStatus.NOT_FOUND : HttpStatus.OK;
            return new ResponseEntity<>(person, status);
        } catch (Exception e) {
            throw new ApiRequestException(e.getLocalizedMessage(), e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<PersonDto> addPerson(@RequestBody PersonDto personDto) {
        try {
            return new ResponseEntity<>(personService.add(personDto), HttpStatus.CREATED);
        } catch (Exception e) {
            throw new ApiRequestException(e.getLocalizedMessage(), e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public void deletePerson(@RequestBody PersonDto personDto) {
        try {
            personService.delete(personDto.getEmail());
            new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            throw new ApiRequestException(e.getLocalizedMessage(), e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
