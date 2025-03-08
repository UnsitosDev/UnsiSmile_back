package edu.mx.unsis.unsiSmile.common;

import edu.mx.unsis.unsiSmile.exceptions.AppException;
import edu.mx.unsis.unsiSmile.model.PersonModel;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

@Component
public class ValidateCurp {

    public void validateCURP(PersonModel person) {
        String expectedCURP = generateCURP(person);
        String actualCURP = person.getCurp();

        if (expectedCURP.length() != actualCURP.length()) {
            throw new AppException(ResponseMessages.CURP_LENGTH_MISMATCH, HttpStatus.BAD_REQUEST);
        }

        for (int i = 0; i < expectedCURP.length(); i++) {
            if (expectedCURP.charAt(i) != '-' && expectedCURP.charAt(i) != actualCURP.charAt(i)) {
                throw new AppException(ResponseMessages.CURP_DATA_MISMATCH, HttpStatus.BAD_REQUEST);
            }
        }
    }

    private static String generateCURP(PersonModel person) {
        StringBuilder curpBuilder = new StringBuilder();

        curpBuilder.append(person.getFirstLastName().toUpperCase().charAt(0));

        curpBuilder.append(getFirstInternalVowel(person.getFirstLastName()));

        curpBuilder.append(person.getSecondLastName().toUpperCase().charAt(0));

        curpBuilder.append(person.getFirstName().toUpperCase().charAt(0));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMdd");
        curpBuilder.append(person.getBirthDate().format(formatter));

        curpBuilder.append(getGenderCode(person.getGender().getGender()));

        curpBuilder.append("--");

        curpBuilder.append(getFirstInternalConsonant(person.getFirstLastName()));
        curpBuilder.append(getFirstInternalConsonant(person.getSecondLastName()));
        curpBuilder.append(getFirstInternalConsonant(person.getFirstName()));

        curpBuilder.append("-");

        curpBuilder.append("-");

        return curpBuilder.toString();
    }

    private static char getFirstInternalVowel(String str) {
        String vowels = "AEIOU";
        for (int i = 1; i < str.length(); i++) {
            char c = Character.toUpperCase(str.charAt(i));
            if (vowels.indexOf(c) != -1) {
                return c;
            }
        }
        return 'X';
    }

    private static char getFirstInternalConsonant(String str) {
        String consonants = "BCDFGHJKLMNÑPQRSTVWXYZ";
        for (int i = 1; i < str.length(); i++) {
            char c = Character.toUpperCase(str.charAt(i));
            if (consonants.indexOf(c) != -1) {
                return c;
            }
        }
        return 'X';
    }

    private static String getGenderCode(String gender) {
        return gender.equalsIgnoreCase("Masculino") ? "H" : "M";
    }
}