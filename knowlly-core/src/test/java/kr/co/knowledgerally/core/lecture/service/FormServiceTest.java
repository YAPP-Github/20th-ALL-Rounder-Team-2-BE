package kr.co.knowledgerally.core.lecture.service;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import kr.co.knowledgerally.core.annotation.KnowllyDataTest;
import kr.co.knowledgerally.core.coach.entity.Coach;
import kr.co.knowledgerally.core.coach.util.TestCoachEntityFactory;
import kr.co.knowledgerally.core.core.exception.ResourceNotFoundException;
import kr.co.knowledgerally.core.lecture.entity.Form;
import kr.co.knowledgerally.core.lecture.util.TestFormEntityFactory;
import kr.co.knowledgerally.core.lecture.entity.Lecture;
import kr.co.knowledgerally.core.lecture.util.TestLectureEntityFactory;
import kr.co.knowledgerally.core.user.entity.User;
import kr.co.knowledgerally.core.user.util.TestUserEntityFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@KnowllyDataTest
@DatabaseSetup({
        "classpath:dbunit/entity/user.xml",
        "classpath:dbunit/entity/coach.xml",
        "classpath:dbunit/entity/category.xml",
        "classpath:dbunit/entity/lecture_information.xml",
        "classpath:dbunit/entity/lecture.xml",
        "classpath:dbunit/entity/form.xml",
})
@Import(FormService.class)
class FormServiceTest {
    @Autowired
    FormService formService;

    @Test
    void ID로_신청서_찾기_테스트() {
        Form form = formService.findById(1L);

        assertEquals(1L, form.getId());
        assertEquals(1L, form.getLecture().getId());
        assertEquals(4L, form.getUser().getId());
        assertEquals("신청서를 받아주세요!", form.getContent());
        assertEquals(Form.State.ACCEPT, form.getState());
        assertEquals(LocalDateTime.of(2022, 6, 16, 22, 48, 17), form.getExpirationDate());
        assertTrue(form.isActive());
        assertEquals(LocalDateTime.of(2022, 6, 13, 22, 48, 17), form.getCreatedAt());
        assertEquals(LocalDateTime.of(2022, 6, 13, 22, 48, 17), form.getUpdatedAt());
    }

    @Test
    void ID로_신청서_찾기_테스트_없으면_throw() {
        assertThrows(ResourceNotFoundException.class, () -> {
            formService.findById(9999L);
        });
    }

    @Test
    void 사용자로_신청서_찾기_테스트() {
        User testUser = new TestUserEntityFactory().createEntity(3L);

        List<Form> forms = formService.findAllByUser(testUser);

        assertEquals(2, forms.size());
        assertEquals(5L, forms.get(0).getId());
        assertEquals(3L, forms.get(0).getLecture().getId());
        assertEquals(3L, forms.get(0).getUser().getId());
        assertEquals("신청 거절해주세요", forms.get(0).getContent());
        assertEquals(Form.State.REJECT, forms.get(0).getState());
        assertEquals(LocalDateTime.of(2022, 6, 16, 22, 51, 3), forms.get(0).getExpirationDate());
        assertTrue(forms.get(0).isActive());
        assertEquals(LocalDateTime.of(2022, 6, 13, 22, 51, 3), forms.get(0).getCreatedAt());
        assertEquals(LocalDateTime.of(2022, 6, 13, 22, 51, 4), forms.get(0).getUpdatedAt());
        assertEquals(2L, forms.get(1).getId());
        assertEquals(2L, forms.get(1).getLecture().getId());
        assertEquals(3L, forms.get(1).getUser().getId());
        assertEquals("제 신청서를 받아주세요!", forms.get(1).getContent());
        assertEquals(Form.State.ACCEPT, forms.get(1).getState());
        assertEquals(LocalDateTime.of(2022, 6, 16, 22, 48, 18), forms.get(1).getExpirationDate());
        assertTrue(forms.get(1).isActive());
        assertEquals(LocalDateTime.of(2022, 6, 13, 22, 48, 18), forms.get(1).getCreatedAt());
        assertEquals(LocalDateTime.of(2022, 6, 13, 22, 48, 18), forms.get(1).getUpdatedAt());
    }

    @Test
    void 사용자로_신청서_목록_찾기_신청서_상태_조회_테스트() {
        User testUser = new TestUserEntityFactory().createEntity(3L);

        List<Form> forms = formService.findAllByUserAndState(testUser, Form.State.ACCEPT);

        assertEquals(1, forms.size());
        assertEquals(2L, forms.get(0).getId());
        assertEquals(2L, forms.get(0).getLecture().getId());
        assertEquals(3L, forms.get(0).getUser().getId());
        assertEquals("제 신청서를 받아주세요!", forms.get(0).getContent());
        assertEquals(Form.State.ACCEPT, forms.get(0).getState());
        assertEquals(LocalDateTime.of(2022, 6, 16, 22, 48, 18), forms.get(0).getExpirationDate());
        assertTrue(forms.get(0).isActive());
        assertEquals(LocalDateTime.of(2022, 6, 13, 22, 48, 18), forms.get(0).getCreatedAt());
        assertEquals(LocalDateTime.of(2022, 6, 13, 22, 48, 18), forms.get(0).getUpdatedAt());
    }

    @Test
    @ExpectedDatabase(value = "classpath:dbunit/expected/crud/form_insert_test.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    void 신청서_삽입_테스트() {
        Form form = new TestFormEntityFactory().createEntity(8L, 4, 3);
        formService.saveForm(form);
    }
    
    @Test
    void 사용자로_신청서_목록_찾기_클래스_상태_조회_테스트() {
        User testUser = new TestUserEntityFactory().createEntity(3L);

        List<Form> forms = formService.findAllByUserAndLectureState(testUser, Lecture.State.ON_GOING);

        assertEquals(1, forms.size());
        assertEquals(2L, forms.get(0).getId());
        assertEquals(2L, forms.get(0).getLecture().getId());
        assertEquals(3L, forms.get(0).getUser().getId());
        assertEquals("제 신청서를 받아주세요!", forms.get(0).getContent());
        assertEquals(Form.State.ACCEPT, forms.get(0).getState());
        assertEquals(LocalDateTime.of(2022, 6, 16, 22, 48, 18), forms.get(0).getExpirationDate());
        assertTrue(forms.get(0).isActive());
        assertEquals(LocalDateTime.of(2022, 6, 13, 22, 48, 18), forms.get(0).getCreatedAt());
        assertEquals(LocalDateTime.of(2022, 6, 13, 22, 48, 18), forms.get(0).getUpdatedAt());
    }

    @Test
    void 클래스_코치로_신청서_목록_찾기_테스트() {
        Coach coach = new TestCoachEntityFactory().createEntity(2L);

        List<Form> forms = formService.findAllByLectureCoach(coach);

        assertEquals(2, forms.size());

        assertEquals(7L, forms.get(0).getId());
        assertEquals(4L, forms.get(0).getLecture().getId());
        assertEquals(2L, forms.get(0).getUser().getId());
        assertEquals("신청서를 받아주세요!", forms.get(0).getContent());
        assertEquals(Form.State.REQUEST, forms.get(0).getState());
        assertEquals(LocalDateTime.of(2022, 6, 16, 22, 51, 5), forms.get(0).getExpirationDate());
        assertTrue(forms.get(0).isActive());
        assertEquals(LocalDateTime.of(2022, 6, 13, 22, 51, 5), forms.get(0).getCreatedAt());
        assertEquals(LocalDateTime.of(2022, 6, 13, 22, 51, 6), forms.get(0).getUpdatedAt());

        assertEquals(4L, forms.get(1).getId());
        assertEquals(4L, forms.get(1).getLecture().getId());
        assertEquals(1L, forms.get(1).getUser().getId());
        assertEquals("신청서를 받아주세요!", forms.get(1).getContent());
        assertEquals(Form.State.REQUEST, forms.get(1).getState());
        assertEquals(LocalDateTime.of(2022, 6, 16, 22, 48, 20), forms.get(1).getExpirationDate());
        assertTrue(forms.get(1).isActive());
        assertEquals(LocalDateTime.of(2022, 6, 13, 22, 48, 20), forms.get(1).getCreatedAt());
        assertEquals(LocalDateTime.of(2022, 6, 13, 22, 48, 20), forms.get(1).getUpdatedAt());
    }

    @Test
    void 클래스_일정으로_신청서_목록_찾기_테스트() {
        Lecture lecture = new TestLectureEntityFactory().createEntity(1);

        List<Form> forms = formService.findAllByLecture(lecture);

        assertEquals(2, forms.size());

        assertEquals(3L, forms.get(0).getId());
        assertEquals(1L, forms.get(0).getLecture().getId());
        assertEquals(2L, forms.get(0).getUser().getId());
        assertEquals("안받아주셔도 되요", forms.get(0).getContent());
        assertEquals(Form.State.REJECT, forms.get(0).getState());
        assertEquals(LocalDateTime.of(2022, 6, 16, 22, 48, 19), forms.get(0).getExpirationDate());
        assertTrue(forms.get(0).isActive());
        assertEquals(LocalDateTime.of(2022, 6, 13, 22, 48, 19), forms.get(0).getCreatedAt());
        assertEquals(LocalDateTime.of(2022, 6, 13, 22, 48, 19), forms.get(0).getUpdatedAt());

        assertEquals(1L, forms.get(1).getId());
        assertEquals(1L, forms.get(1).getLecture().getId());
        assertEquals(4L, forms.get(1).getUser().getId());
        assertEquals("신청서를 받아주세요!", forms.get(1).getContent());
        assertEquals(Form.State.ACCEPT, forms.get(1).getState());
        assertEquals(LocalDateTime.of(2022, 6, 16, 22, 48, 17), forms.get(1).getExpirationDate());
        assertTrue(forms.get(1).isActive());
        assertEquals(LocalDateTime.of(2022, 6, 13, 22, 48, 17), forms.get(1).getCreatedAt());
        assertEquals(LocalDateTime.of(2022, 6, 13, 22, 48, 17), forms.get(1).getUpdatedAt());
    }
}