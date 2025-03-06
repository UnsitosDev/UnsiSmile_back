package edu.mx.unsis.unsiSmile.common;

import edu.mx.unsis.unsiSmile.model.PersonModel;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;

@Component
public class ValidationUtils {

    public String getFullNameFromPerson(PersonModel person) {
        return person.getFirstName() + " " +
                (person.getSecondName() != null ? person.getSecondName() + " " : "") +
                person.getFirstLastName() + " " +
                person.getSecondLastName();
    }

    public boolean isMinor(LocalDate birthDate) {
        LocalDate today = LocalDate.now();
        return Period.between(birthDate, today).getYears() < 18;
    }
}
