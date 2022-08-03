package kr.co.knowledgerally.api.lecture.service;

import kr.co.knowledgerally.core.coach.entity.Coach;
import kr.co.knowledgerally.core.coach.repository.CoachRepository;
import kr.co.knowledgerally.core.coach.util.TestCoachEntityFactory;
import kr.co.knowledgerally.core.lecture.entity.Category;
import kr.co.knowledgerally.core.lecture.entity.Lecture;
import kr.co.knowledgerally.core.lecture.entity.LectureImage;
import kr.co.knowledgerally.core.lecture.entity.LectureInformation;
import kr.co.knowledgerally.core.lecture.repository.CategoryRepository;
import kr.co.knowledgerally.core.lecture.repository.LectureImageRepository;
import kr.co.knowledgerally.core.lecture.repository.LectureInformationRepository;
import kr.co.knowledgerally.core.lecture.repository.LectureRepository;
import kr.co.knowledgerally.core.lecture.service.LectureService;
import kr.co.knowledgerally.core.lecture.util.TestCategoryEntityFactory;
import kr.co.knowledgerally.core.lecture.util.TestLectureEntityFactory;
import kr.co.knowledgerally.core.lecture.util.TestLectureImageEntityFactory;
import kr.co.knowledgerally.core.lecture.util.TestLectureInformationEntityFactory;
import kr.co.knowledgerally.core.user.entity.User;
import kr.co.knowledgerally.core.user.repository.UserRepository;
import kr.co.knowledgerally.core.user.util.TestUserEntityFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.LinkedHashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class LectureDeleteServiceTest {
    @Autowired
    LectureDeleteService lectureDeleteService;
    @Autowired
    LectureRepository lectureRepository;
    @Autowired
    LectureInformationRepository lectureInformationRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    CoachRepository coachRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    LectureImageRepository lectureImageRepository;
    TestLectureEntityFactory testLectureEntityFactory = new TestLectureEntityFactory();
    TestLectureInformationEntityFactory testLectureInformationEntityFactory = new TestLectureInformationEntityFactory();
    TestCategoryEntityFactory testCategoryEntityFactory = new TestCategoryEntityFactory();
    TestCoachEntityFactory testCoachEntityFactory = new TestCoachEntityFactory();
    TestUserEntityFactory testUserEntityFactory = new TestUserEntityFactory();
    TestLectureImageEntityFactory testLectureImageEntityFactory = new TestLectureImageEntityFactory();

    @Test
    void 클래스_일정_삭제_테스트() {
        Lecture lecture = testLectureEntityFactory.createEntity(1);
        LectureInformation lectureInfo = testLectureInformationEntityFactory.createEntity(1L);
        Category category = testCategoryEntityFactory.createEntity(1L);
        Coach coach = testCoachEntityFactory.createEntity(1L);
        User user = testUserEntityFactory.createEntity(1L);
        LectureImage image1 = testLectureImageEntityFactory.createEntity(1L);
        LectureImage image2 = testLectureImageEntityFactory.createEntity(2L);

        Set<LectureImage> lectureImages = new LinkedHashSet<>();
        lectureImages.add(image1);
        lectureImages.add(image2);

        lecture.setLectureInformation(lectureInfo);
        lecture.setState(Lecture.State.ON_BOARD);
        lectureInfo.setCategory(category);
        lectureInfo.setCoach(coach);
        lectureInfo.setLectureImages(lectureImages);
        coach.setUser(user);

        userRepository.save(user);
        categoryRepository.save(category);
        coachRepository.save(coach);
        lectureImageRepository.saveAll(lectureImages);
        lectureInformationRepository.save(lectureInfo);
        lecture = lectureRepository.saveAndFlush(lecture);

        lecture = lectureDeleteService.delete(lecture.getId());
        assertEquals(1L, lecture.getId());
        assertFalse(lecture.isActive());
    }
}
