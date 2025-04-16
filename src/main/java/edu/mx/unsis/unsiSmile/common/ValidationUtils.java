package edu.mx.unsis.unsiSmile.common;

import edu.mx.unsis.unsiSmile.model.PersonModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ValidationUtils {
    private final ValidateCurp curpValidator;

    public String getFullNameFromPerson(PersonModel person) {
        return person.getFirstName() + " " +
                ((person.getSecondName() != null && !person.getSecondName().trim().isEmpty()) ? person.getSecondName() + " " : "") +
                person.getFirstLastName() + " " +
                person.getSecondLastName();
    }

    public boolean isMinor(LocalDate birthDate) {
        LocalDate today = LocalDate.now();
        return Period.between(birthDate, today).getYears() < 18;
    }

    public boolean validateCURP(PersonModel person, List<String> invalidCurps) {
        return curpValidator.validateCURP(person, invalidCurps);
    }

    public boolean validateCurpStructure(String curp, List<String> invalidCurps) {
        return curpValidator.validateCurpStructure(curp, invalidCurps);
    }
}
