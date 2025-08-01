package edu.mx.unsis.unsiSmile.service.professors;

import edu.mx.unsis.unsiSmile.authenticationProviders.dtos.RegisterRequest;
import edu.mx.unsis.unsiSmile.authenticationProviders.model.ERole;
import edu.mx.unsis.unsiSmile.authenticationProviders.model.UserModel;
import edu.mx.unsis.unsiSmile.common.Constants;
import edu.mx.unsis.unsiSmile.common.ResponseMessages;
import edu.mx.unsis.unsiSmile.dtos.request.professors.ProfessorRequest;
import edu.mx.unsis.unsiSmile.dtos.response.professors.ProfessorResponse;
import edu.mx.unsis.unsiSmile.exceptions.AppException;
import edu.mx.unsis.unsiSmile.mappers.professors.ProfessorMapper;
import edu.mx.unsis.unsiSmile.model.people.PersonModel;
import edu.mx.unsis.unsiSmile.model.professors.ProfessorModel;
import edu.mx.unsis.unsiSmile.model.students.CareerModel;
import edu.mx.unsis.unsiSmile.repository.professors.IProfessorClinicalAreaRepository;
import edu.mx.unsis.unsiSmile.repository.professors.IProfessorRepository;
import edu.mx.unsis.unsiSmile.service.UserService;
import edu.mx.unsis.unsiSmile.service.medicalHistories.PersonService;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProfessorService {
    private final IProfessorRepository professorRepository;
    private final UserService userService;
    private final ProfessorMapper professorMapper;
    private final PersonService personService;
    private final IProfessorClinicalAreaRepository professorClinicalAreaRepository;
    private final ProfessorClinicalAreaService professorClinicalAreaService;

    @Transactional
    public void createProfessor(@NotNull ProfessorRequest request) {
        try {
            List<String> invalidCurp = new ArrayList<>();
            PersonModel personModel = personService.createPersonEntity(request.getPerson(), invalidCurp);
            if(!invalidCurp.isEmpty()) {
                throw new AppException(invalidCurp.getFirst(), HttpStatus.BAD_REQUEST);
            }

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
    public void updateProfessor(String professorId, ProfessorRequest request) {
        try {
            ProfessorModel professorModel = professorRepository.findById(professorId)
                .orElseThrow(() -> new AppException(ResponseMessages.PROFESSOR_NOT_FOUND +
                        professorId, HttpStatus.NOT_FOUND));

            updateProfessorDetails(professorModel, request);

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

    @Transactional
    public void toggleProfessorStatus(String employeeNumber) {
        try {
            ProfessorModel professorModel = professorRepository.findById(employeeNumber)
                    .orElseThrow(() -> new AppException("Professor not found", HttpStatus.NOT_FOUND));

            boolean isCurrentlyActive = Constants.ACTIVE.equals(professorModel.getStatusKey());
            String newStatus = isCurrentlyActive ? Constants.INACTIVE : Constants.ACTIVE;

            if (isCurrentlyActive) {
                professorClinicalAreaRepository.findByProfessorAndStatusKey(professorModel, Constants.ACTIVE)
                        .ifPresent(clinicalArea -> professorClinicalAreaService.toggleProfessorClinicalAreaStatus(
                                clinicalArea.getIdProfessorClinicalArea()));
            }

            professorModel.setStatusKey(newStatus);
            professorRepository.save(professorModel);
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException("Fail to toggle professor status", HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }

    private void updateProfessorDetails(ProfessorModel professorModel, ProfessorRequest request) {
        if (request.getCareer() != null && request.getCareer().getIdCareer() != null) {
            professorModel.setCareer(CareerModel.builder().idCareer(request.getCareer().getIdCareer()).build());
        }

        if (request.getPerson() != null) {
            PersonModel updatedPerson = personService.updatedPerson(professorModel.getPerson().getCurp(), request.getPerson());
            professorModel.setPerson(updatedPerson);
        }
    }
}
