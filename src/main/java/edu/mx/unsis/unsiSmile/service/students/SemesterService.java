package edu.mx.unsis.unsiSmile.service.students;

import java.util.List;

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

            SemesterModel semesterModel = semesterMapper.toEntity(request);
            semesterRepository.save(semesterModel);
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
}
