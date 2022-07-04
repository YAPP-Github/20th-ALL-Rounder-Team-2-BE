package kr.co.knowledgerally.api.core.validation;


import kr.co.knowledgerally.api.core.validation.annotation.DateFormat;
import kr.co.knowledgerally.api.core.validation.annotation.DateTimeFormat;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DateTimeStringValidatorTest extends AbstractValidatorTest {
    @Test
    public void 날짜시간문자열_검증_단순_테스트() {
        DateTimeStringValidator validator = new DateTimeStringValidator();
        assertTrue(validator.isValid("2020-10-14T22:35:42", null));
        assertTrue(validator.isValid("2020-10-14T22:35", null));
        assertFalse(validator.isValid("20202-10-14T22:35:42", null));
        assertFalse(validator.isValid("2020-13-14T22:35:42", null));
        assertFalse(validator.isValid("2020-12-14T25:35:42", null));
    }

    @Test
    public void 날짜시간문자열_검증_심화_테스트() {
        SimpleDto simpleDto = new SimpleDto("2020-10-14T22:35:42");
        Set<ConstraintViolation<SimpleDto>> violations = validator.validate(simpleDto);
        assertTrue(violations.isEmpty());

        simpleDto = new SimpleDto("2020-10-14T22:35");
        violations = validator.validate(simpleDto);
        assertTrue(violations.isEmpty());

        simpleDto = new SimpleDto("20202-10-14T22:35:42");
        violations = validator.validate(simpleDto);
        assertFalse(violations.isEmpty());

        simpleDto = new SimpleDto("2020-13-14T22:35:42");
        violations = validator.validate(simpleDto);
        assertFalse(violations.isEmpty());

        simpleDto = new SimpleDto("2020-12-14 25:35:42");
        violations = validator.validate(simpleDto);
        assertFalse(violations.isEmpty());
    }

    private static class SimpleDto {
        SimpleDto(String dateTimeString) {
            this.dateTimeString = dateTimeString;
        }

        @DateTimeFormat
        private final String dateTimeString;

        public String getDateTimeString() {
            return dateTimeString;
        }
    }
}