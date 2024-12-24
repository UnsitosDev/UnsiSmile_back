package edu.mx.unsis.unsiSmile.mappers.patients;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import edu.mx.unsis.unsiSmile.dtos.request.patients.PatientRequest;
import edu.mx.unsis.unsiSmile.dtos.response.patients.PatientResponse;
import edu.mx.unsis.unsiSmile.mappers.BaseMapper;
import edu.mx.unsis.unsiSmile.mappers.PersonMapper;
import edu.mx.unsis.unsiSmile.mappers.addresses.AddressMapper;
import edu.mx.unsis.unsiSmile.model.addresses.NationalityModel;
import edu.mx.unsis.unsiSmile.model.patients.EthnicGroupModel;
import edu.mx.unsis.unsiSmile.model.patients.MaritalStatusModel;
import edu.mx.unsis.unsiSmile.model.patients.OccupationModel;
import edu.mx.unsis.unsiSmile.model.patients.PatientModel;
import edu.mx.unsis.unsiSmile.model.patients.ReligionModel;
import lombok.AllArgsConstructor;

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

    @Override
    public PatientModel toEntity(PatientRequest dto) {
        return PatientModel.builder()
                .admissionDate(dto.getAdmissionDate())
                .isMinor(dto.getIsMinor())
                .hasDisability(dto.getHasDisability())
                .nationality(NationalityModel.builder().idNationality(dto.getNationalityId()).build())
                .person(personMapper.toEntity(dto.getPerson()))
                .address(addressMapper.toEntity(dto.getAddress()))
                .maritalStatus(MaritalStatusModel.builder().idMaritalStatus(dto.getMaritalStatusId()).build())
                .occupation(OccupationModel.builder().idOccupation(dto.getOccupationId()).build())
                .ethnicGroup(EthnicGroupModel.builder().idEthnicGroup(dto.getEthnicGroupId()).build())
                .religion(ReligionModel.builder().idReligion(dto.getReligionId()).build())
                .guardian(dto.getGuardian() != null ? guardianMapper.toEntity(dto.getGuardian()) : null)
                .build();
    }

    @Override
    public PatientResponse toDto(PatientModel entity) {
        return PatientResponse.builder()
                .idPatient(entity.getIdPatient())
                .admissionDate(entity.getAdmissionDate())
                .isMinor(entity.getIsMinor())
                .hasDisability(entity.getHasDisability())
                .nationality(nationalityMapper.toDto(entity.getNationality()))
                .person(personMapper.toDto(entity.getPerson()))
                .address(addressMapper.toDto(entity.getAddress()))
                .maritalStatus(maritalStatusMapper.toDto(entity.getMaritalStatus()))
                .occupation(occupationMapper.toDto(entity.getOccupation()))
                .ethnicGroup(ethnicGroupMapper.toDto(entity.getEthnicGroup()))
                .religion(religionMapper.toDto(entity.getReligion()))
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
        entity.setAdmissionDate(request.getAdmissionDate());
        entity.setIsMinor(request.getIsMinor());
        entity.setHasDisability(request.getHasDisability());
        entity.setNationality(NationalityModel.builder().idNationality(request.getNationalityId()).build());
        entity.setPerson(personMapper.toEntity(request.getPerson()));
        entity.setAddress(addressMapper.toEntity(request.getAddress()));
        entity.setMaritalStatus(MaritalStatusModel.builder().idMaritalStatus(request.getMaritalStatusId()).build());
        entity.setOccupation(OccupationModel.builder().idOccupation(request.getOccupationId()).build());
        entity.setEthnicGroup(EthnicGroupModel.builder().idEthnicGroup(request.getEthnicGroupId()).build());
        entity.setReligion(ReligionModel.builder().idReligion(request.getReligionId()).build());
        entity.setGuardian(guardianMapper.toEntity(request.getGuardian()));
    }
}
