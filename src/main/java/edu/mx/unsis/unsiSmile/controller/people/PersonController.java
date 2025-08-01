package edu.mx.unsis.unsiSmile.controller.people;

import edu.mx.unsis.unsiSmile.dtos.request.people.PersonRequest;
import edu.mx.unsis.unsiSmile.dtos.response.people.PersonResponse;
import edu.mx.unsis.unsiSmile.service.people.PersonService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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