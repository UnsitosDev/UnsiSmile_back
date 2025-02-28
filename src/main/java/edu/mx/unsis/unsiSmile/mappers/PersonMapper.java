package edu.mx.unsis.unsiSmile.mappers;

import edu.mx.unsis.unsiSmile.dtos.request.PersonRequest;
import edu.mx.unsis.unsiSmile.dtos.response.PersonResponse;
import edu.mx.unsis.unsiSmile.model.GenderModel;
import edu.mx.unsis.unsiSmile.model.PersonModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PersonMapper implements BaseMapper<PersonResponse, PersonRequest, PersonModel> {

    @Autowired
    private GenderMapper genderMapper;

    @Override
    public PersonModel toEntity(PersonRequest dto) {
        return PersonModel.builder()
                .curp(dto.getCurp())
                .firstName(dto.getFirstName())
                .secondName(dto.getSecondName())
                .firstLastName(dto.getFirstLastName())
                .secondLastName(dto.getSecondLastName())
                .phone(dto.getPhone())
                .birthDate(dto.getBirthDate())
                .email(dto.getEmail())
                .gender(genderMapper.toEntity(dto.getGender()))
                .build();
    }

    public PersonModel toEntity(PersonResponse dto) {
        return PersonModel.builder()
                .curp(dto.getCurp())
                .firstName(dto.getFirstName())
                .secondName(dto.getSecondName())
                .firstLastName(dto.getFirstLastName())
                .secondLastName(dto.getSecondLastName())
                .phone(dto.getPhone())
                .birthDate(dto.getBirthDate())
                .email(dto.getEmail())
                .build();
    }

    @Override
    public PersonResponse toDto(PersonModel entity) {
        return PersonResponse.builder()
                .curp(entity.getCurp())
                .firstName(entity.getFirstName())
                .secondName(entity.getSecondName())
                .firstLastName(entity.getFirstLastName())
                .secondLastName(entity.getSecondLastName())
                .phone(entity.getPhone())
                .birthDate(entity.getBirthDate())
                .email(entity.getEmail())
                .gender(genderMapper.toDto(entity.getGender()))
                .build();
    }

    @Override
    public List<PersonResponse> toDtos(List<PersonModel> entities) {
        return entities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void updateEntity(PersonRequest request, PersonModel entity) {
        entity.setFirstName((request.getFirstName() != null && !request.getFirstName().isBlank()) ? request.getFirstName() : entity.getFirstName());
        entity.setSecondName((request.getSecondName() != null && !request.getSecondName().isBlank()) ? request.getSecondName() : entity.getSecondName());
        entity.setFirstLastName((request.getFirstLastName() != null && !request.getFirstLastName().isBlank()) ? request.getFirstLastName() : entity.getFirstLastName());
        entity.setSecondLastName((request.getSecondLastName() != null && !request.getSecondLastName().isBlank()) ? request.getSecondLastName() : entity.getSecondLastName());
        entity.setPhone((request.getPhone() != null && !request.getPhone().isBlank()) ? request.getPhone() : entity.getPhone());
        entity.setBirthDate(request.getBirthDate() != null ? request.getBirthDate() : entity.getBirthDate());
        entity.setEmail((request.getEmail() != null && !request.getEmail().isBlank()) ? request.getEmail() : entity.getEmail());
        entity.setGender((request.getGender() != null && request.getGender().getIdGender() != null)
                ? GenderModel.builder().idGender(request.getGender().getIdGender()).build()
                : entity.getGender());
    }
}