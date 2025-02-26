package edu.mx.unsis.unsiSmile.service.students;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import edu.mx.unsis.unsiSmile.dtos.request.students.SemesterRequest;
import edu.mx.unsis.unsiSmile.dtos.response.students.SemesterResponse;
import edu.mx.unsis.unsiSmile.exceptions.AppException;
import edu.mx.unsis.unsiSmile.mappers.students.SemesterMapper;
import edu.mx.unsis.unsiSmile.model.students.SemesterModel;
import edu.mx.unsis.unsiSmile.repository.students.ISemesterRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SemesterService {

    private final ISemesterRepository semesterRepository;
    private final SemesterMapper semesterMapper;

    @Transactional
    public void createSemester(@NonNull SemesterRequest request) {
        try {
            Assert.notNull(request, "SemesterRequest cannot be null");

            if (request.getEndDate().isBefore(request.getStartDate())) {
                throw new AppException("End date cannot be before start date", HttpStatus.BAD_REQUEST);
            }

            SemesterModel semesterModel = semesterMapper.toEntity(request);

            String semesterName = this.getSemesterName(request.getStartDate(), request.getEndDate(), request.getCycle().getCycleName());
            semesterModel.setSemesterName(semesterName);

            semesterRepository.disableAllSemesters();

            semesterRepository.save(semesterModel);
        } catch (AppException e) {
            throw e;
        } catch (Exception ex) {
            throw new AppException("Failed to create semester", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public SemesterResponse getSemesterById(@NonNull Long id) {
        try {
            SemesterModel semesterModel = semesterRepository.findById(id)
                    .orElseThrow(() -> new AppException("Semester not found with ID: " + id, HttpStatus.NOT_FOUND));
            return semesterMapper.toDto(semesterModel);
        } catch (EmptyResultDataAccessException ex) {
            throw new AppException("Semester not found with ID: " + id, HttpStatus.NOT_FOUND, ex);
        } catch (Exception ex) {
            throw new AppException("Failed to fetch semester", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public List<SemesterResponse> getAllSemesters() {
        try {
            List<SemesterModel> allSemesters = semesterRepository.findAll();
            return semesterMapper.toDtos(allSemesters);
        } catch (Exception ex) {
            throw new AppException("Failed to fetch semesters", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional
    public void updateSemester(@NonNull Long id, @NonNull SemesterRequest updatedSemesterRequest) {
        try {
            Assert.notNull(updatedSemesterRequest, "Updated SemesterRequest cannot be null");

            SemesterModel semesterModel = semesterRepository.findById(id)
                    .orElseThrow(() -> new AppException("Semester not found with ID: " + id, HttpStatus.NOT_FOUND));

            semesterMapper.updateEntity(updatedSemesterRequest, semesterModel);

            semesterRepository.save(semesterModel);
        } catch (Exception ex) {
            throw new AppException("Failed to update semester", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional
    public void deleteSemesterById(@NonNull Long id) {
        try {
            if (!semesterRepository.existsById(id)) {
                throw new AppException("Semester not found with ID: " + id, HttpStatus.NOT_FOUND);
            }
            semesterRepository.deleteById(id);
        } catch (EmptyResultDataAccessException ex) {
            throw new AppException("Semester not found with ID: " + id, HttpStatus.NOT_FOUND, ex);
        } catch (Exception ex) {
            throw new AppException("Failed to delete semester", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public Optional<SemesterModel> getActiveSemester() {
        try {
            return semesterRepository.findActiveSemester();
        } catch (Exception ex) {
            throw new AppException("Failed to fetch active semester", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    private String getSemesterName(LocalDate startDate, LocalDate endDate, String cycle) {
        try {
            Assert.notNull(startDate, "Fecha de inicio cannot be null");
            Assert.notNull(endDate, "Fecha de fin cannot be null");
            String[] inicio = startDate.toString().split("-");
            String[] fin = endDate.toString().split("-");
            
            String semesterName = inicio[0] + "-" + fin[0] + "-" + cycle;
            
            return semesterName;
        } catch (Exception ex) {
            throw new AppException("Failed to get semester name", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public SemesterResponse getCurrentSemester() {
        try {
            Optional<SemesterModel> activeSemester = getActiveSemester();
            if (activeSemester.isEmpty()) {
                throw new AppException("No active semester found", HttpStatus.NOT_FOUND);
            }
            return semesterMapper.toDto(activeSemester.get());
        } catch (AppException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new AppException("Failed to fetch current semester", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }
}
