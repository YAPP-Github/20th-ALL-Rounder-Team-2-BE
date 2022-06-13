package kr.co.knowledgerally.api.core.validation;

import kr.co.knowledgerally.api.core.validation.annotation.TimeFormat;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalTime;

/**
 * HH:mm:ss 형태의 문자열을 검증한다.
 */
public class TimeStringValidator implements ConstraintValidator<TimeFormat, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        try {
            LocalTime.parse(value);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
