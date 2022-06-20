package kr.co.knowledgerally.api.core.jwt.component;

import kr.co.knowledgerally.core.core.component.DateFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

class JwtValidatorTest extends AbstractJwtTest {
    @InjectMocks
    private JwtValidator jwtValidator;

    @Mock
    private DateFactory dateFactory;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(jwtValidator, "secretKey", TEST_SECRET_KEY);
    }

    @Test
    public void 액세스_토큰_유효_테스트() {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Seoul"), Locale.KOREA);
        calendar.set(2080, Calendar.OCTOBER, 22, 11, 58, 29); // 생성일은 21일
        when(dateFactory.now()).thenReturn(calendar.getTime());

        assertTrue(jwtValidator.validateToken(TEST_ACCESS_TOKEN));
    }

    @Test
    public void 액세스_토큰_만료_테스트() {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Seoul"), Locale.KOREA);
        calendar.set(2080, Calendar.OCTOBER, 22, 11, 58, 30);
        when(dateFactory.now()).thenReturn(calendar.getTime());

        assertFalse(jwtValidator.validateToken(TEST_ACCESS_TOKEN));
    }

    @Test
    public void 리프레시_토큰_유효_테스트() {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Seoul"), Locale.KOREA);
        calendar.set(2080, Calendar.DECEMBER, 20, 11, 58, 29); // 생성일은 10월 21일
        when(dateFactory.now()).thenReturn(calendar.getTime());

        assertTrue(jwtValidator.validateToken(TEST_REFRESH_TOKEN));
    }

    @Test
    public void 리프레시_토큰_만료_테스트() {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Seoul"), Locale.KOREA);
        calendar.set(2080, Calendar.DECEMBER, 20, 11, 58, 30);
        when(dateFactory.now()).thenReturn(calendar.getTime());

        assertFalse(jwtValidator.validateToken(TEST_REFRESH_TOKEN));
    }
}