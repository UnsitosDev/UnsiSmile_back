package edu.mx.unsis.unsiSmile.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.mx.unsis.unsiSmile.dtos.request.PersonRequest;
import edu.mx.unsis.unsiSmile.dtos.response.PersonResponse;
import edu.mx.unsis.unsiSmile.service.medicalHistories.PersonService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/unsismile/api/v1/persons")
public class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @PostMapping
    public ResponseEntity<PersonResponse> createPerson(@Valid @RequestBody PersonRequest personRequest) {
        PersonResponse createdPerson = personService.createPerson(personRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPerson);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonResponse> getPersonById(@Valid @PathVariable String id) {
        PersonResponse personResponse = personService.getPersonById(id);
        return ResponseEntity.ok(personResponse);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<PersonResponse> getPersonByEmail(@Valid @PathVariable String email) {
        PersonResponse personResponse = personService.getPersonByEmail(email);
        return ResponseEntity.ok(personResponse);
    }

    @GetMapping
    public ResponseEntity<List<PersonResponse>> getAllPersons() {
        List<PersonResponse> allPersons = personService.getAllPersons();
        return ResponseEntity.ok(allPersons);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PersonResponse> updatePerson(@Valid @PathVariable String id, @Valid @RequestBody PersonRequest updatedPersonRequest) {
        PersonResponse updatedPerson = personService.updatePerson(id, updatedPersonRequest);
        return ResponseEntity.ok(updatedPerson);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePersonById(@Valid @PathVariable String id) {
        personService.deletePersonById(id);
        return ResponseEntity.noContent().build();
    }
}