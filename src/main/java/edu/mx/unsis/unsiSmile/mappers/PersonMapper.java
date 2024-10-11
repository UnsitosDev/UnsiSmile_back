package edu.mx.unsis.unsiSmile.mappers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.mx.unsis.unsiSmile.dtos.request.PersonRequest;
import edu.mx.unsis.unsiSmile.dtos.response.PersonResponse;
import edu.mx.unsis.unsiSmile.model.PersonModel;

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
        // entity.setFirstName(request.getFirstName());
        // entity.setSecondName(request.getSecondName());
        // entity.setFirstLastName(request.getFirstLastName());
        // entity.setSecondLastName(request.getSecondLastName());
        // entity.setPhone(request.getPhone());
        // entity.setBirthDate(request.getBirthDate());
        // entity.setEmail(request.getEmail());
        // entity.setGender(GenderModel.builder().id(request.getGenderId()).build());
    }
}