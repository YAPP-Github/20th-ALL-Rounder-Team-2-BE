package kr.co.knowledgerally.core.core.annotation;

import kr.co.knowledgerally.core.core.vo.EventType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface PublishEvent {
    Class<? extends EventType<?>> eventType();

    // SpEL('#{표현식}')
    String spel() default "";
}
