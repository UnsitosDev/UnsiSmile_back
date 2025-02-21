package edu.mx.unsis.unsiSmile.service.professors;

import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import edu.mx.unsis.unsiSmile.authenticationProviders.dtos.RegisterRequest;
import edu.mx.unsis.unsiSmile.authenticationProviders.model.ERole;
import edu.mx.unsis.unsiSmile.authenticationProviders.model.UserModel;
import edu.mx.unsis.unsiSmile.common.Constants;
import edu.mx.unsis.unsiSmile.dtos.request.professors.ProfessorRequest;
import edu.mx.unsis.unsiSmile.dtos.response.professors.ProfessorResponse;
import edu.mx.unsis.unsiSmile.exceptions.AppException;
import edu.mx.unsis.unsiSmile.mappers.professors.ProfessorMapper;
import edu.mx.unsis.unsiSmile.model.PersonModel;
import edu.mx.unsis.unsiSmile.model.professors.ProfessorModel;
import edu.mx.unsis.unsiSmile.repository.professors.IProfessorRepository;
import edu.mx.unsis.unsiSmile.service.UserService;
import edu.mx.unsis.unsiSmile.service.medicalHistories.PersonService;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProfessorService {
    private final IProfessorRepository professorRepository;
    private final UserService userService;
    private final ProfessorMapper professorMapper;
    private final PersonService personService;

    @Transactional
    public void createProfessor(@NotNull ProfessorRequest request) {
        try {
            PersonModel personModel = personService.createPersonEntity(request.getPerson());

            UserModel userModel = userService.createUser(
                setCredentials(request.getEmployeeNumber(), request.getPerson().getCurp()));

            ProfessorModel professorModel = professorMapper.toEntity(request);

            professorModel.setPerson(personModel);
            professorModel.setUser(userModel);

            professorRepository.save(professorModel);

        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException("Fail to create professor", HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }

    @Transactional
    public ProfessorResponse getProfessor(String employeeNumber) {
        try {
            ProfessorModel professorModel = professorRepository.findById(employeeNumber)
                .orElseThrow(() -> new AppException("Professor not found", HttpStatus.NOT_FOUND));

            return professorMapper.toDto(professorModel);
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException("Fail to get professor", HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }

    @Transactional
    public Page<ProfessorResponse> getAllProfessors(Pageable pageable, String keyword) {
        try {
            Page<ProfessorModel> professors;

            if (keyword == null || keyword.isEmpty()) {
                professors  = professorRepository.findAll(pageable);
            } else {
                professors = professorRepository.findAllBySearchInput(keyword, pageable);
            }

            return professors.map(professorMapper::toDto);
        } catch (Exception e) {
            throw new AppException("Fail to get professors", HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }

    @Transactional
    public void updateProfessor(ProfessorRequest request) {
        try {
            ProfessorModel professorModel = professorRepository.findById(request.getEmployeeNumber())
                .orElseThrow(() -> new AppException("Professor not found", HttpStatus.NOT_FOUND));
            
            professorMapper.updateEntity(request, professorModel);

            professorRepository.save(professorModel);
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException("Fail to update professor", HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }
    
    @Transactional
    public void deleteProfessor(String employeeNumber) {
        try {
            Optional<ProfessorModel> professorModel = professorRepository.findById(employeeNumber);

            professorModel.ifPresentOrElse(
                professor  -> {
                    professor.setStatusKey(Constants.INACTIVE);
                    professorRepository.save(professor);

                    userService.deleteUser(employeeNumber);
                },
                () -> {
                    throw new AppException("Professor not found with ID: " + employeeNumber, HttpStatus.NOT_FOUND);
                }
            );
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException("Fail to delete professor", HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }

    private RegisterRequest setCredentials(String employeeNumber, String curp) {
        return RegisterRequest.builder()
                .password(curp)
                .username(employeeNumber)
                .role(ERole.ROLE_PROFESSOR.toString())
                .build();
    }
}
