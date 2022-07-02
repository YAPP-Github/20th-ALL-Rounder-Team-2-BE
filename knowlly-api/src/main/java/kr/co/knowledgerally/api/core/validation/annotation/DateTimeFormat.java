package kr.co.knowledgerally.api.core.validation.annotation;

import kr.co.knowledgerally.api.core.validation.DateStringValidator;
import kr.co.knowledgerally.api.core.validation.DateTimeStringValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
@Constraint(validatedBy = DateTimeStringValidator.class)
@Documented
public @interface DateTimeFormat {
    String message() default "Date format should be like yyyy-MM-dd HH:mm:ss";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
