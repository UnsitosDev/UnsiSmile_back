package edu.mx.unsis.unsiSmile.service.medicalHistories;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import edu.mx.unsis.unsiSmile.common.ValidationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import edu.mx.unsis.unsiSmile.common.ResponseMessages;
import edu.mx.unsis.unsiSmile.dtos.request.people.PersonRequest;
import edu.mx.unsis.unsiSmile.dtos.response.people.PersonResponse;
import edu.mx.unsis.unsiSmile.exceptions.AppException;
import edu.mx.unsis.unsiSmile.mappers.people.PersonMapper;
import edu.mx.unsis.unsiSmile.model.people.PersonModel;
import edu.mx.unsis.unsiSmile.repository.people.IPersonRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PersonService {

    private final IPersonRepository personRepository;
    private final PersonMapper personMapper;
    private final ValidationUtils validationUtils;

    @Transactional
    public PersonResponse createPerson(@NonNull PersonRequest personRequest) {
        try {
            Assert.notNull(personRequest, "PersonRequest cannot be null");

            if (personRepository.existsById(personRequest.getCurp())) {
                throw new AppException(String.format(ResponseMessages.PERSON_ALREADY_EXISTS, personRequest.getCurp()),
                        HttpStatus.CONFLICT);
            }

            PersonModel personModel = personMapper.toEntity(personRequest);

            List<String> invalidCurps = new ArrayList<>();
            if (!validationUtils.validateCURP(personModel, invalidCurps)) {
                throw new AppException(invalidCurps.getFirst(), HttpStatus.BAD_REQUEST);
            }

            PersonModel savedPerson = personRepository.save(personModel);

            return personMapper.toDto(savedPerson);
        } catch (AppException e) {
            throw e;
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.FAILED_CREATE_PERSON, HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public PersonResponse getPersonById(@NonNull String personId) {
        try {
            return personRepository.findById(personId)
                    .map(personMapper::toDto)
                    .orElse(null);
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.FAILED_FETCH_PERSON, HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public PersonResponse getPersonByEmail(@NonNull String email) {
        try {
            // Find the person in the database by email
            PersonModel personModel = personRepository.findByEmail(email)
                    .orElseThrow(() -> new AppException(String.format(ResponseMessages.PERSON_NOT_FOUND_EMAIL, email),
                            HttpStatus.NOT_FOUND));

            // Map the entity to a response DTO
            return personMapper.toDto(personModel);
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.FAILED_FETCH_PERSON, HttpStatus.INTERNAL_SERVER_ERROR, ex);
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
            throw new AppException(ResponseMessages.FAILED_FETCH_PERSONS, HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional
    public PersonResponse updatePerson(@NonNull String personId, @NonNull PersonRequest updatedPersonRequest) {
        try {
            Assert.notNull(updatedPersonRequest, ResponseMessages.UPDATED_PERSON_REQUEST_NULL);

            return personMapper.toDto(updatedPerson(personId, updatedPersonRequest));
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.FAILED_UPDATE_PERSON, HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional
    public void deletePersonById(@NonNull String personId) {
        try {
            // Check if the person exists
            if (!personRepository.existsById(personId)) {
                throw new AppException(String.format(ResponseMessages.PERSON_NOT_FOUND_ID, personId),
                        HttpStatus.NOT_FOUND);
            }
            personRepository.deleteById(personId);
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.FAILED_DELETE_PERSON, HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional
    public PersonModel createPersonEntity(PersonRequest personRequest, List<String> invalidCurps) {
        Assert.notNull(personRequest, ResponseMessages.PERSON_REQUEST_NULL);

        PersonResponse existingPerson = this.getPersonById(personRequest.getCurp());
        if (existingPerson != null) {
            return personMapper.toEntity(existingPerson);
        }

        PersonModel personModel = personMapper.toEntity(personRequest);

        if (!validationUtils.validateCURP(personModel, invalidCurps)) {
            return null;
        }

        return personRepository.save(personModel);
    }

    @Transactional
    public PersonModel updatedPerson(String personId, PersonRequest request) {
        PersonModel personModel = personRepository.findById(personId)
                .orElseThrow(() -> new AppException(
                        String.format(ResponseMessages.PERSON_NOT_FOUND, personId),
                        HttpStatus.NOT_FOUND));
        personMapper.updateEntity(request, personModel);

        List<String> invalidCurps = new ArrayList<>();
        if (!validationUtils.validateCURP(personModel, invalidCurps)) {
            throw new AppException(invalidCurps.getFirst(), HttpStatus.BAD_REQUEST);
        }

        return personRepository.save(personModel);
    }

    @Transactional(readOnly = true)
    public PersonResponse getPersonByCurp(@NonNull String curp) {
        try {
            return personRepository.findByCurp(curp)
                    .map(personMapper::toDto)
                    .orElseThrow(() -> new AppException(
                            String.format(ResponseMessages.PERSON_NOT_FOUND, curp),
                            HttpStatus.NOT_FOUND));
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.FAILED_FETCH_PERSON, HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public PersonModel getPersonModelByCurp(@NonNull String curp) {
        try {
            return personRepository.findByCurp(curp)
                    .orElseThrow(() -> new AppException(
                            String.format(ResponseMessages.PERSON_NOT_FOUND, curp),
                            HttpStatus.NOT_FOUND));
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.FAILED_FETCH_PERSON, HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }
}