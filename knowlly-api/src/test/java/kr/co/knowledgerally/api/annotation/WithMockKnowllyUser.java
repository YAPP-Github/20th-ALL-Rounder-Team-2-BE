package kr.co.knowledgerally.api.annotation;


import kr.co.knowledgerally.api.config.WithMockKnowllyUserSecurityContextFactory;
import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithMockKnowllyUserSecurityContextFactory.class)
public @interface WithMockKnowllyUser {
    long userId() default 1;
}
