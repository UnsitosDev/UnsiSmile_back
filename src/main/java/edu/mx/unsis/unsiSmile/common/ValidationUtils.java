package edu.mx.unsis.unsiSmile.common;

import edu.mx.unsis.unsiSmile.model.PersonModel;
import org.springframework.stereotype.Component;

@Component
public class ValidationUtils {

    public String getFullNameFromPerson(PersonModel person) {
        return person.getFirstName() + " " +
                (person.getSecondName() != null ? person.getSecondName() + " " : "") +
                person.getFirstLastName() + " " +
                person.getSecondLastName();
    }
}
