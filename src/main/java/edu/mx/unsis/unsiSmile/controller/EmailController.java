package edu.mx.unsis.unsiSmile.controller;

import edu.mx.unsis.unsiSmile.dtos.request.EmailRequest;
import edu.mx.unsis.unsiSmile.service.emails.EmailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Email services")
@RestController
@RequestMapping("/unsismile/api/v1/emails")
@AllArgsConstructor
@Validated
public class EmailController {
    private final EmailService emailService;

    @Operation(summary = "Envía un correo")
    @PostMapping
    public ResponseEntity<Void> sendEmail(@Valid @RequestBody EmailRequest request) {
        emailService.sendEmail(request);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}