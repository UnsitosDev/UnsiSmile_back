package edu.mx.unsis.unsiSmile.common;

import edu.mx.unsis.unsiSmile.exceptions.AppException;
import edu.mx.unsis.unsiSmile.model.PersonModel;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ValidationUtils {
    private final ValidateCurp curpValidator;

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

    /**
     * Valida y construye el rango de fechas de inicio y fin.
     * - Si solo startDate viene, endDate será hoy.
     * - Si alguna fecha es futura o startDate > endDate → lanza AppException.
     */
    public Optional<Pair<Timestamp, Timestamp>> resolveDateRange(LocalDate startDate, LocalDate endDate) {
        LocalDate today = LocalDate.now();

        if (startDate != null && startDate.isAfter(today)) {
            throw new AppException(ResponseMessages.START_DATE_CANNOT_BE_AFTER_TODAY, HttpStatus.BAD_REQUEST);
        }

        if (endDate != null && endDate.isAfter(today)) {
            throw new AppException(ResponseMessages.END_DATE_CANNOT_BE_AFTER_TODAY, HttpStatus.BAD_REQUEST);
        }

        if (startDate != null && endDate != null && startDate.isAfter(endDate)) {
            throw new AppException(ResponseMessages.END_DATE_MUST_BE_GREATER_THAN_START_DATE, HttpStatus.BAD_REQUEST) ;
        }

        if (startDate == null && endDate == null) {
            return Optional.empty();
        }

        if (startDate != null && endDate == null) {
            endDate = today;
        }

        Timestamp startTimestamp = Timestamp.valueOf(startDate.atStartOfDay());
        Timestamp endTimestamp = Timestamp.valueOf(endDate.plusDays(1).atStartOfDay());

        return Optional.of(Pair.of(startTimestamp, endTimestamp));
    }
}
