package kr.co.knowledgerally.core.lecture.service;

import kr.co.knowledgerally.core.coach.entity.Coach;
import kr.co.knowledgerally.core.coach.repository.CoachRepository;
import kr.co.knowledgerally.core.coach.service.CoachService;
import kr.co.knowledgerally.core.core.exception.ResourceNotFoundException;
import kr.co.knowledgerally.core.core.message.ErrorMessage;
import kr.co.knowledgerally.core.lecture.entity.Category;
import kr.co.knowledgerally.core.lecture.entity.LectureImage;
import kr.co.knowledgerally.core.lecture.entity.LectureInformation;
import kr.co.knowledgerally.core.lecture.entity.Tag;
import kr.co.knowledgerally.core.lecture.repository.LectureImageRepository;
import kr.co.knowledgerally.core.lecture.repository.LectureInformationRepository;
import kr.co.knowledgerally.core.lecture.repository.TagRepository;
import kr.co.knowledgerally.core.user.entity.User;
import kr.co.knowledgerally.core.user.repository.UserRepository;
import kr.co.knowledgerally.core.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

//@Validated
@Service
@RequiredArgsConstructor
public class LectureInformationService {
    private final LectureInformationRepository lectureInformationRepository;
    private final TagRepository tagRepository;
    private final CoachRepository coachRepository;
    private final LectureImageRepository lectureImageRepository;
    private final CoachService coachService;
    private final CategoryService categoryService;
    private final UserRepository userRepository;

    /**
     * 클래스-info 목록을 조회합니다.
     * @return 클래스-info 리스트
     */
    @Transactional(readOnly = true)
    public Page<LectureInformation> findAllWithPageable(Pageable pageable) {
        return lectureInformationRepository.findAllTop10ByIsActiveOrderByIdDesc(true, pageable);
    }

    /**
     * 해당 카테고리 속하는 클래스-info 목록을 조회합니다.
     * @param category 검색하고자 하는 카테고리
     * @return 클래스-info 리스트
     */
    @Transactional(readOnly = true)
    public Page<LectureInformation> findAllByCategoryWithPageable(Category category, Pageable pageable) {
        return lectureInformationRepository.findAllByCategoryAndIsActiveOrderByIdDesc(category, true, pageable);
    }

    /**
     * 해당 카테고리 이름으로 클래스-info 목록을 검색합니다.
     * @param categoryName 검색하고자 하는 카테고리 이름
     * @return 클래스-info 리스트
     */
    @Transactional(readOnly = true)
    public List<LectureInformation> searchAllByCategoryName(String categoryName) {
        try {
            return lectureInformationRepository.findAllByCategoryNameAndIsActiveOrderByIdDesc(Category.Name.valueOf(categoryName), true);
        } catch (IllegalArgumentException e) {
            return Collections.emptyList();
        }
    }

    /**
     * 제목으로 클래스-info 목록을 검색합니다.
     * @param topic 검색하고자 하는 클래스 제목
     * @return 클래스-info 리스트
     */
    @Transactional(readOnly = true)
    public List<LectureInformation> searchAllByTopic(String topic) {
        return lectureInformationRepository.findAllByTopicContainingAndIsActiveOrderByIdDesc(topic, true);
    }

    /**
     * 클래스 정보 ID로 클래스 정보 객체를 찾습니다.
     * @param id 클래스 정보 ID
     * @return 클래스-info 객체
     * @throws ResourceNotFoundException 해당 id가 존재하지 않을 경우
     */
    @Transactional(readOnly = true)
    public LectureInformation findById(Long id) throws ResourceNotFoundException {
        return lectureInformationRepository.findByIdAndIsActive(id, true).orElseThrow(() ->
                new ResourceNotFoundException(ErrorMessage.NOT_EXIST_LECTURE_INFO));
    }

    /**
     * 클래스-info와 클래스 태그들을 저장합니다.
     * @param lectureInformation 저장하고자 하는 클래스-info 엔티티
     * @param user 현재 로그인된 유저
     * @return 저장된 클래스-info 엔티티
     */
    @Transactional
    public LectureInformation saveLectureInformation(@Valid LectureInformation lectureInformation, User user) {
        Coach coach = getOrCreateCoach(user);
        Category category = categoryService.findByName(lectureInformation.getCategory().getName()).orElseThrow();
        lectureInformation.setCoach(coach);
        lectureInformation.setCategory(category);
        lectureInformationRepository.save(lectureInformation);

        Set<Tag> tagSet = lectureInformation.getTags();
        tagSet.stream().forEach(tag-> tag.setLectureInformation(lectureInformation));

        Set<LectureImage> lectureImageSet = lectureInformation.getLectureImages();
        Set<LectureImage> newLectureImageSet = new LinkedHashSet<>();

        for (LectureImage element : lectureImageSet) {
            LectureImage lectureImage = lectureImageRepository.findById(element.getId()).orElseThrow();
            lectureImage.setLectureInformation(lectureInformation);
            lectureImageRepository.save(lectureImage);
            newLectureImageSet.add(lectureImage);
        }

        tagRepository.saveAllAndFlush(tagSet);
        lectureInformation.setTags(tagSet);
        lectureInformation.setLectureImages(newLectureImageSet);

        return lectureInformation;
    }

    private Coach getOrCreateCoach(User user) {
        Coach coach = coachService.findByUser(user);

        if (coach == null) {
            coach = new Coach();
            coach.setUser(user);
            coach.setIntroduce(user.getIntro());
            user.setCoach(true);
            coachRepository.save(coach);
            userRepository.save(user);
        }
        return coach;
    }
}
