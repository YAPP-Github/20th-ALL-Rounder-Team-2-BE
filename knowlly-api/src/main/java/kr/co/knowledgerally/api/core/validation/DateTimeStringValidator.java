package kr.co.knowledgerally.api.core.validation;

import kr.co.knowledgerally.api.core.validation.annotation.DateFormat;
import kr.co.knowledgerally.api.core.validation.annotation.DateTimeFormat;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * yyyy-mm-dd HH:mm:ss 형태의 문자열 유효성 검증
 */
public class DateTimeStringValidator implements ConstraintValidator<DateTimeFormat, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        try {
            LocalDateTime.parse(value);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
