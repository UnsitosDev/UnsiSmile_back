package edu.mx.unsis.unsiSmile.mappers.students;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.mx.unsis.unsiSmile.dtos.request.students.StudentPatientRequest;
import edu.mx.unsis.unsiSmile.dtos.response.students.StudentPatientResponse;
import edu.mx.unsis.unsiSmile.mappers.BaseMapper;
import edu.mx.unsis.unsiSmile.mappers.patients.PatientMapper;
import edu.mx.unsis.unsiSmile.model.patients.PatientModel;
import edu.mx.unsis.unsiSmile.model.students.StudentModel;
import edu.mx.unsis.unsiSmile.model.students.StudentPatientModel;

@Component
public class StudentPatientMapper implements BaseMapper<StudentPatientResponse, StudentPatientRequest, StudentPatientModel> {

    @Autowired
    private PatientMapper patientMapper;
    @Autowired
    private StudentMapper studentMapper;

    @Override
    public StudentPatientModel toEntity(StudentPatientRequest dto) {
        return StudentPatientModel.builder()
                .idStudentPatient(dto.getIdStudentPatient() != null ? dto.getIdStudentPatient() : 0)
                .patient(PatientModel.builder().idPatient(dto.getPatientId()).build())
                .student(StudentModel.builder().enrollment(dto.getStudentId()).build())
                .build();
    }

    @Override
    public StudentPatientResponse toDto(StudentPatientModel entity) {
        return StudentPatientResponse.builder()
                .idStudentPatient(entity.getIdStudentPatient())
                .patient(patientMapper.toDto(entity.getPatient()))
                .student(studentMapper.toDto(entity.getStudent()))
                .build();
    }

    @Override
    public List<StudentPatientResponse> toDtos(List<StudentPatientModel> entities) {
        return entities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void updateEntity(StudentPatientRequest request, StudentPatientModel entity) {
        // entity.setPatient(patientMapper.toEntity(PatientModel.builder().idPatient(request.getPatientId()).build()));
        // entity.setStudent(studentMapper.toEntity(StudentModel.builder().idStudent(request.getStudentId()).build()));
    }
}
