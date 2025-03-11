package edu.mx.unsis.unsiSmile.common;

import edu.mx.unsis.unsiSmile.model.PersonModel;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.regex.Pattern;

@Component
public class ValidateCurp {

    private static final Pattern CURP_REGEX = Pattern.compile(
            "^[A-Z][AEIOUX][A-Z]{2}\\d{2}(?:0[1-9]|1[0-2])(?:0[1-9]|[12]\\d|3[01])[HM]" +
                    "(?:AS|B[CS]|C[CLMSH]|D[FG]|G[TR]|HG|JC|M[CNS]|N[ETL]|OC|PL|Q[TR]|S[PLR]|T[CSL]|VZ|YN|ZS)" +
                    "[B-DF-HJ-NP-TV-Z]{3}[A-Z\\d]\\d$"
    );

    public boolean validateCurpStructure(String curp, List<String> invalidCurps) {
        if (curp == null || curp.trim().isEmpty()) {
            if (invalidCurps != null) {
                invalidCurps.add(ResponseMessages.NOT_BLANK_CURP);
            }
            return false;
        }

        if (!CURP_REGEX.matcher(curp).matches()) {
            if (invalidCurps != null) {
                invalidCurps.add(String.format(ResponseMessages.INVALID_CURP_FORMAT, curp));
            }
            return false;
        }

        return true;
    }

    public boolean validateCURP(PersonModel person, List<String> invalidCurps) {
        String curp = person.getCurp();

        if (!validateCurpStructure(curp, invalidCurps)) {
            return false;
        }

        return validateDateAndGender(person, curp, invalidCurps);
    }

    private boolean validateDateAndGender(PersonModel person, String curp, List<String> invalidCurps) {
        String curpDate = curp.substring(4, 10);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMdd");
        String expectedDate = person.getBirthDate().format(formatter);

        if (!curpDate.equals(expectedDate)) {
            invalidCurps.add(String.format(ResponseMessages.INVALID_CURP_BIRTHDATE, curp));
            return false;
        }

        return true;
    }
}