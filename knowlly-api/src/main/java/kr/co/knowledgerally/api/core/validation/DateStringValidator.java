package kr.co.knowledgerally.api.core.validation;

import kr.co.knowledgerally.api.core.validation.annotation.DateFormat;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

/**
 * yyyy-mm-dd 형태의 문자열 유효성 검증
 */
public class DateStringValidator implements ConstraintValidator<DateFormat, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        try {
            LocalDate.parse(value);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
