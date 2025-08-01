package edu.mx.unsis.unsiSmile.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import edu.mx.unsis.unsiSmile.dtos.request.people.PersonRequest;
import edu.mx.unsis.unsiSmile.dtos.response.people.PersonResponse;
import edu.mx.unsis.unsiSmile.service.medicalHistories.PersonService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/unsismile/api/v1/people")
public class PersonController {

    private final PersonService personService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<PersonResponse> createPerson(@Valid @RequestBody PersonRequest request) {
        return ResponseEntity.ok(personService.createPerson(request));
    }

    @PatchMapping("/{curp}")
    public ResponseEntity<PersonResponse> updatePerson(
            @PathVariable String curp, @RequestBody PersonRequest request) {
        return ResponseEntity.ok(personService.updatePerson(curp, request));
    }

    @GetMapping("/{curp}")
    public ResponseEntity<PersonResponse> getPersonByCURP(@PathVariable String curp) {
        return ResponseEntity.ok(personService.getPersonByCurp(curp));
    }

}
