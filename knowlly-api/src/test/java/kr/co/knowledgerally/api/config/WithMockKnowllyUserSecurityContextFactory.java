package kr.co.knowledgerally.api.config;

import kr.co.knowledgerally.api.annotation.WithMockKnowllyUser;
import kr.co.knowledgerally.api.core.service.UserDetailsImpl;
import kr.co.knowledgerally.core.user.util.TestUserEntityFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

public class WithMockKnowllyUserSecurityContextFactory implements WithSecurityContextFactory<WithMockKnowllyUser> {
    @Override
    public SecurityContext createSecurityContext(WithMockKnowllyUser customUser) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        TestUserEntityFactory userEntityFactory = new TestUserEntityFactory();

        UserDetailsImpl principal =
                new UserDetailsImpl(userEntityFactory.createEntity(1L));
        Authentication auth =
                new UsernamePasswordAuthenticationToken(principal, "password", principal.getAuthorities());
        context.setAuthentication(auth);
        return context;
    }
}