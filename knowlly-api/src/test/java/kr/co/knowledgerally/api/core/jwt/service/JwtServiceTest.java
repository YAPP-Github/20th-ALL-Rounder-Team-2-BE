package kr.co.knowledgerally.api.core.jwt.service;

import kr.co.knowledgerally.api.core.jwt.component.JwtCreator;
import kr.co.knowledgerally.api.core.jwt.component.JwtResolver;
import kr.co.knowledgerally.api.core.jwt.component.JwtValidator;
import kr.co.knowledgerally.api.core.jwt.dto.JwtToken;
import kr.co.knowledgerally.api.core.jwt.dto.ProviderToken;
import kr.co.knowledgerally.api.core.jwt.dto.TokenProvider;
import kr.co.knowledgerally.api.core.oauth2.dto.OAuth2Profile;
import kr.co.knowledgerally.api.core.oauth2.service.OAuth2ServiceFactory;
import kr.co.knowledgerally.core.core.component.DateFactory;
import kr.co.knowledgerally.core.core.exception.UnauthorizedException;
import kr.co.knowledgerally.core.user.entity.RefreshToken;
import kr.co.knowledgerally.core.user.entity.User;
import kr.co.knowledgerally.core.user.repository.RefreshTokenRepository;
import kr.co.knowledgerally.core.user.service.UserService;
import kr.co.knowledgerally.core.user.util.TestRefreshTokenEntityFactory;
import kr.co.knowledgerally.core.user.util.TestUserEntityFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JwtServiceTest {
    private static final String TEST_PROVIDER_ACCESS_TOKEN = "testProviderToken";
    private static final String TEST_KNOWLLY_ACCESS_TOKEN = "testKnowllyAccessToken";
    private static final String TEST_KNOWLLY_REFRESH_TOKEN = "testKnowllyRefreshToken";
    private static final String TEST_NEW_REFRESH_TOKEN = "testNewRefreshToken";
    private static final String TEST_EXIST_IDENTIFIER = "identifier1";

    @InjectMocks
    private JwtService jwtService;

    @Mock
    private JwtCreator jwtCreator;

    @Mock
    private JwtResolver jwtResolver;

    @Mock
    private JwtValidator jwtValidator;

    @Mock
    private OAuth2ServiceFactory oAuth2ServiceFactory;

    @Mock
    private UserService userService;

    @Mock
    private RefreshTokenRepository refreshTokenRepository;

    @Mock
    private DateFactory dateFactory;

    private final TestUserEntityFactory testUserEntityFactory = new TestUserEntityFactory();
    private final TestRefreshTokenEntityFactory testRefreshTokenEntityFactory = new TestRefreshTokenEntityFactory();
    private User testUser;
    private RefreshToken testRefreshToken;

    @BeforeEach
    void setUp() {
        testUser = testUserEntityFactory.createEntity(1L);
        testRefreshToken = testRefreshTokenEntityFactory.createEntity(1L);
        testRefreshToken.setValue(TEST_KNOWLLY_REFRESH_TOKEN);
    }

    @Test
    void JWT_토큰_발급_테스트() {
        when(userService.findByIdentifier(eq(TEST_EXIST_IDENTIFIER))).thenReturn(testUser);
        when(jwtCreator.createAccessToken(eq(testUser))).thenReturn(TEST_KNOWLLY_ACCESS_TOKEN);
        when(oAuth2ServiceFactory.getInstance(eq(TokenProvider.KAKAO)))
                .thenReturn(providerAccessToken -> new OAuth2Profile(TEST_EXIST_IDENTIFIER, "테스트이름"));
        when(jwtCreator.createRefreshToken(eq(testUser))).thenReturn(TEST_KNOWLLY_REFRESH_TOKEN);

        ProviderToken providerToken = new ProviderToken(TokenProvider.KAKAO, TEST_PROVIDER_ACCESS_TOKEN);
        JwtToken jwtToken = jwtService.issue(providerToken);

        assertEquals(TEST_KNOWLLY_ACCESS_TOKEN, jwtToken.getKnowllyAccessToken());
        assertEquals(TEST_KNOWLLY_REFRESH_TOKEN, jwtToken.getKnowllyRefreshToken());
    }

    @Test
    void 리프레시_토큰_발급_테스트() {
        when(userService.findByIdentifier(eq(TEST_EXIST_IDENTIFIER))).thenReturn(testUser);
        when(refreshTokenRepository.findByUser(eq(testUser))).thenReturn(Optional.of(testRefreshToken));
        when(jwtCreator.createAccessToken(eq(testUser))).thenReturn(TEST_KNOWLLY_ACCESS_TOKEN);
        when(jwtValidator.validateToken(eq(TEST_KNOWLLY_REFRESH_TOKEN))).thenReturn(true);
        when(jwtResolver.getUserIdentifier(eq(TEST_KNOWLLY_REFRESH_TOKEN))).thenReturn(TEST_EXIST_IDENTIFIER);
        when(jwtValidator.validateTokenBefore(eq(TEST_KNOWLLY_REFRESH_TOKEN), any())).thenReturn(true);

        JwtToken.Refresh refreshToken = new JwtToken.Refresh(TEST_KNOWLLY_REFRESH_TOKEN);
        JwtToken jwtToken = jwtService.refresh(refreshToken);

        assertEquals(TEST_KNOWLLY_ACCESS_TOKEN, jwtToken.getKnowllyAccessToken());
        assertEquals(TEST_KNOWLLY_REFRESH_TOKEN, jwtToken.getKnowllyRefreshToken());
    }

    @Test
    void 리프레시_토큰_발급_리프레시_테스트() {
        when(userService.findByIdentifier(eq(TEST_EXIST_IDENTIFIER))).thenReturn(testUser);
        when(refreshTokenRepository.findByUser(eq(testUser))).thenReturn(Optional.of(testRefreshToken));
        when(jwtCreator.createAccessToken(eq(testUser))).thenReturn(TEST_KNOWLLY_ACCESS_TOKEN);
        when(jwtValidator.validateToken(eq(TEST_KNOWLLY_REFRESH_TOKEN))).thenReturn(true);
        when(jwtResolver.getUserIdentifier(eq(TEST_KNOWLLY_REFRESH_TOKEN))).thenReturn(TEST_EXIST_IDENTIFIER);
        when(jwtValidator.validateTokenBefore(eq(TEST_KNOWLLY_REFRESH_TOKEN), any())).thenReturn(false);
        when(jwtCreator.createRefreshToken(eq(testUser))).thenReturn(TEST_NEW_REFRESH_TOKEN);

        JwtToken.Refresh refreshToken = new JwtToken.Refresh(TEST_KNOWLLY_REFRESH_TOKEN);
        JwtToken jwtToken = jwtService.refresh(refreshToken);

        assertEquals(TEST_KNOWLLY_ACCESS_TOKEN, jwtToken.getKnowllyAccessToken());
        assertEquals(TEST_NEW_REFRESH_TOKEN, jwtToken.getKnowllyRefreshToken());
    }

    @Test
    void 리프레시_토큰_발급_만료_테스트() {
        when(jwtValidator.validateToken(eq(TEST_KNOWLLY_REFRESH_TOKEN))).thenReturn(false);

        assertThrows(UnauthorizedException.class, () -> {
            JwtToken.Refresh refreshToken = new JwtToken.Refresh(TEST_KNOWLLY_REFRESH_TOKEN);
            JwtToken jwtToken = jwtService.refresh(refreshToken);

            assertEquals(TEST_KNOWLLY_ACCESS_TOKEN, jwtToken.getKnowllyAccessToken());
            assertEquals(TEST_NEW_REFRESH_TOKEN, jwtToken.getKnowllyRefreshToken());
        });
    }

    @Test
    void 리프레시_토큰_발급_저장소와_맞지않음_테스트() {
        when(jwtValidator.validateToken(eq(TEST_KNOWLLY_REFRESH_TOKEN))).thenReturn(true);
        when(jwtResolver.getUserIdentifier(eq(TEST_KNOWLLY_REFRESH_TOKEN))).thenReturn(TEST_EXIST_IDENTIFIER);
        when(userService.findByIdentifier(eq(TEST_EXIST_IDENTIFIER))).thenReturn(testUser);
        testRefreshToken.setValue("wrong-value");
        when(refreshTokenRepository.findByUser(eq(testUser))).thenReturn(Optional.of(testRefreshToken));

        assertThrows(UnauthorizedException.class, () -> {
            JwtToken.Refresh refreshToken = new JwtToken.Refresh(TEST_KNOWLLY_REFRESH_TOKEN);
            JwtToken jwtToken = jwtService.refresh(refreshToken);

            assertEquals(TEST_KNOWLLY_ACCESS_TOKEN, jwtToken.getKnowllyAccessToken());
            assertEquals(TEST_NEW_REFRESH_TOKEN, jwtToken.getKnowllyRefreshToken());
        });
    }
}