package edu.mx.unsis.unsiSmile.mappers.patients;

import edu.mx.unsis.unsiSmile.common.ValidationUtils;
import edu.mx.unsis.unsiSmile.dtos.request.patients.PatientRequest;
import edu.mx.unsis.unsiSmile.dtos.response.patients.PatientResponse;
import edu.mx.unsis.unsiSmile.mappers.BaseMapper;
import edu.mx.unsis.unsiSmile.mappers.PersonMapper;
import edu.mx.unsis.unsiSmile.mappers.addresses.AddressMapper;
import edu.mx.unsis.unsiSmile.model.patients.demographics.NationalityModel;
import edu.mx.unsis.unsiSmile.model.patients.*;
import edu.mx.unsis.unsiSmile.model.patients.demographics.EthnicGroupModel;
import edu.mx.unsis.unsiSmile.model.patients.demographics.MaritalStatusModel;
import edu.mx.unsis.unsiSmile.model.patients.demographics.OccupationModel;
import edu.mx.unsis.unsiSmile.model.patients.demographics.ReligionModel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class PatientMapper implements BaseMapper<PatientResponse, PatientRequest, PatientModel> {

    private NationalityMapper nationalityMapper;
    private AddressMapper addressMapper;
    private MaritalStatusMapper maritalStatusMapper;
    private OccupationMapper occupationMapper;
    private EthnicGroupMapper ethnicGroupMapper;
    private ReligionMapper religionMapper;
    private GuardianMapper guardianMapper;
    private PersonMapper personMapper;
    private ValidationUtils validationUtils;

    @Override
    public PatientModel toEntity(PatientRequest dto) {
        return PatientModel.builder()
                .admissionDate(LocalDate.now())
                .hasDisability(dto.getHasDisability())
                .nationality(NationalityModel.builder().idNationality(dto.getNationalityId()).build())
                .person(personMapper.toEntity(dto.getPerson()))
                .address(addressMapper.toEntity(dto.getAddress()))
                .maritalStatus(MaritalStatusModel.builder().idMaritalStatus(dto.getMaritalStatus().getIdMaritalStatus()).build())
                .occupation(OccupationModel.builder().idOccupation(dto.getOccupation().getIdOccupation()).build())
                .ethnicGroup(EthnicGroupModel.builder().idEthnicGroup(dto.getEthnicGroup().getIdEthnicGroup()).build())
                .religion(ReligionModel.builder().idReligion(dto.getReligion().getIdReligion()).build())
                .guardian(dto.getGuardian() != null ? guardianMapper.toEntity(dto.getGuardian()) : null)
                .medicalRecordNumber(null)
                .build();
    }

    @Override
    public PatientResponse toDto(PatientModel entity) {
        return PatientResponse.builder()
                .idPatient(entity.getIdPatient())
                .admissionDate(entity.getAdmissionDate())
                .medicalRecordNumber(entity.getMedicalRecordNumber())
                .hasDisability(entity.getHasDisability())
                .nationality(nationalityMapper.toDto(entity.getNationality()))
                .person(personMapper.toDto(entity.getPerson()))
                .address(addressMapper.toDto(entity.getAddress()))
                .maritalStatus(maritalStatusMapper.toDto(entity.getMaritalStatus()))
                .occupation(occupationMapper.toDto(entity.getOccupation()))
                .ethnicGroup(ethnicGroupMapper.toDto(entity.getEthnicGroup()))
                .religion(religionMapper.toDto(entity.getReligion()))
                .isMinor(validationUtils.isMinor(entity.getPerson().getBirthDate()))
                .guardian(entity.getGuardian() != null ? guardianMapper.toDto(entity.getGuardian()) : null)
                .build();
    }

    @Override
    public List<PatientResponse> toDtos(List<PatientModel> entities) {
        return entities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void updateEntity(PatientRequest request, PatientModel entity) {
        entity.setHasDisability(request.getHasDisability() != null ? request.getHasDisability() :
                entity.getHasDisability());
        entity.setNationality(request.getNationalityId() != null ?
                NationalityModel.builder().idNationality(request.getNationalityId()).build() : entity.getNationality());
        entity.setMaritalStatus(request.getMaritalStatus() != null ?
                MaritalStatusModel.builder().idMaritalStatus(request.getMaritalStatus().getIdMaritalStatus()).build() : entity.getMaritalStatus());
        entity.setEthnicGroup(request.getEthnicGroup() != null ?
                EthnicGroupModel.builder().idEthnicGroup(request.getEthnicGroup().getIdEthnicGroup()).build() : entity.getEthnicGroup());
        entity.setReligion(request.getReligion() != null ?
                ReligionModel.builder().idReligion(request.getReligion().getIdReligion()).build() : entity.getReligion());
    }
}
