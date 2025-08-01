package edu.mx.unsis.unsiSmile.mappers.users;

import edu.mx.unsis.unsiSmile.dtos.response.users.DigitizerUserResponse;
import edu.mx.unsis.unsiSmile.model.digitizers.MedicalRecordDigitizerModel;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class DigitizerResponseBuilder {

    public DigitizerUserResponse build(MedicalRecordDigitizerModel userModel) {

        return DigitizerUserResponse.builder()
                .username(userModel.getUser().getUsername())
                .build();
    }
}
