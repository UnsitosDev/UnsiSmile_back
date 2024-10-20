package edu.mx.unsis.unsiSmile.service.medicalHistories;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import edu.mx.unsis.unsiSmile.dtos.request.PersonRequest;
import edu.mx.unsis.unsiSmile.dtos.response.PersonResponse;
import edu.mx.unsis.unsiSmile.exceptions.AppException;
import edu.mx.unsis.unsiSmile.mappers.PersonMapper;
import edu.mx.unsis.unsiSmile.model.PersonModel;
import edu.mx.unsis.unsiSmile.repository.IPersonRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PersonService {

    private final IPersonRepository personRepository;
    private final PersonMapper personMapper;

    @Transactional
    public PersonResponse createPerson(@NonNull PersonRequest personRequest) {
        try {
            Assert.notNull(personRequest, "PersonRequest cannot be null");

            if(personRepository.existsById(personRequest.getCurp())){
              throw new AppException("Person with " + personRequest.getCurp() + " already exists", HttpStatus.CONFLICT);
            }

            // Map the DTO request to the entity
            PersonModel personModel = personMapper.toEntity(personRequest);

            // Save the entity to the database
            PersonModel savedPerson = personRepository.save(personModel);

            // Map the saved entity back to a response DTO
            return personMapper.toDto(savedPerson);
        } catch (Exception ex) {
            throw new AppException("Failed to create person", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public PersonResponse getPersonById(@NonNull String personId) {
        try {
            // Find the person in the database
            PersonModel personModel = personRepository.findById(personId)
                    .orElseThrow(() -> new AppException("Person not found with ID: " + personId, HttpStatus.NOT_FOUND));

            // Map the entity to a response DTO
            return personMapper.toDto(personModel);
        } catch (Exception ex) {
            throw new AppException("Failed to fetch person", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public PersonResponse getPersonByEmail(@NonNull String email) {
        try {
            // Find the person in the database by email
            PersonModel personModel = personRepository.findByEmail(email)
                    .orElseThrow(() -> new AppException("Person not found with email: " + email, HttpStatus.NOT_FOUND));

            // Map the entity to a response DTO
            return personMapper.toDto(personModel);
        } catch (Exception ex) {
            throw new AppException("Failed to fetch person", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public List<PersonResponse> getAllPersons() {
        try {
            List<PersonModel> allPersons = personRepository.findAll();
            return allPersons.stream()
                    .map(personMapper::toDto)
                    .collect(Collectors.toList());
        } catch (Exception ex) {
            throw new AppException("Failed to fetch persons", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional
    public PersonResponse updatePerson(@NonNull String personId, @NonNull PersonRequest updatedPersonRequest) {
        try {
            Assert.notNull(updatedPersonRequest, "Updated PersonRequest cannot be null");

            // Find the person in the database
            PersonModel personModel = personRepository.findById(personId)
                    .orElseThrow(() -> new AppException("Person not found with ID: " + personId, HttpStatus.NOT_FOUND));

            // Update the person entity with the new data
            personMapper.updateEntity(updatedPersonRequest, personModel);

            // Save the updated entity
            PersonModel updatedPerson = personRepository.save(personModel);

            // Map the updated entity back to a response DTO
            return personMapper.toDto(updatedPerson);
        } catch (Exception ex) {
            throw new AppException("Failed to update person", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional
    public void deletePersonById(@NonNull String personId) {
        try {
            // Check if the person exists
            if (!personRepository.existsById(personId)) {
                throw new AppException("Person not found with ID: " + personId, HttpStatus.NOT_FOUND);
            }
            personRepository.deleteById(personId);
        } catch (Exception ex) {
            throw new AppException("Failed to delete person", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }
}