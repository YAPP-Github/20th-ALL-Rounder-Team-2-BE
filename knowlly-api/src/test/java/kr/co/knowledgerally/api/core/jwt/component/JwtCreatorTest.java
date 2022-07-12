package kr.co.knowledgerally.api.core.jwt.component;

import kr.co.knowledgerally.core.core.component.DateFactory;
import kr.co.knowledgerally.core.user.util.TestUserEntityFactory;
import liquibase.pro.packaged.T;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class JwtCreatorTest extends AbstractJwtTest {
    @InjectMocks
    private JwtCreator jwtCreator;

    @Mock
    private DateFactory dateFactory;

    private final TestUserEntityFactory testUserEntityFactory = new TestUserEntityFactory();

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(jwtCreator, "secretKey", TEST_SECRET_KEY);
        ReflectionTestUtils.setField(jwtCreator, "ACCESS_TOKEN_VALID_MILISECONDS", 86400000L);
        ReflectionTestUtils.setField(jwtCreator, "REFRESH_TOKEN_VALID_MILISECONDS", 5184000000L);
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Seoul"), Locale.KOREA);
        calendar.set(2080, Calendar.OCTOBER, 21, 11, 58, 30);
        when(dateFactory.now()).thenReturn(calendar.getTime());
    }

    @Test
    public void 액세스_토큰_생성_테스트() {
        assertEquals(TEST_ACCESS_TOKEN, jwtCreator.createAccessToken(testUserEntityFactory.createEntity(1L)));;
    }

    @Test
    public void 리프레시_토큰_생성_테스트() {
        assertEquals(TEST_REFRESH_TOKEN, jwtCreator.createRefreshToken(testUserEntityFactory.createEntity(1L)));;
    }
}