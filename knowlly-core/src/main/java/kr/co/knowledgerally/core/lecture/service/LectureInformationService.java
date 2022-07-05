package kr.co.knowledgerally.core.lecture.service;

import kr.co.knowledgerally.core.coach.entity.Coach;
import kr.co.knowledgerally.core.coach.service.CoachService;
import kr.co.knowledgerally.core.core.exception.ResourceNotFoundException;
import kr.co.knowledgerally.core.core.message.ErrorMessage;
import kr.co.knowledgerally.core.lecture.entity.Category;
import kr.co.knowledgerally.core.lecture.entity.LectureInformation;
import kr.co.knowledgerally.core.lecture.repository.LectureInformationRepository;
import kr.co.knowledgerally.core.user.entity.User;
import kr.co.knowledgerally.core.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;

import static kr.co.knowledgerally.core.core.message.ErrorMessage.NOT_EXIST_LECTURE_INFO;

//@Validated
@Service
@RequiredArgsConstructor
public class LectureInformationService {
    private final LectureInformationRepository lectureInformationRepository;
    private final CoachService coachService;
    private final CategoryService categoryService;

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
        return lectureInformationRepository.findAllByCategoryCategoryNameAndIsActiveOrderByIdDesc(categoryName, true);
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
        return lectureInformationRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(ErrorMessage.NOT_EXIST_LECTURE_INFO));
    }

    /**
     * 클래스-info를 저장합니다.
     * @param lectureInformation 저장하고자 하는 클래스-info 엔티티
     * @return 저장된 클래스-info 엔티티
     */
    @Transactional
    public LectureInformation saveLectureInformation(Long categoryId, @Valid LectureInformation lectureInformation, User loggedInUser) {
        Coach coach = coachService.findByUser(loggedInUser);
        Category category = categoryService.findById(categoryId).orElseThrow();
        lectureInformation.setCoach(coach);
        lectureInformation.setCategory(category);
        return lectureInformationRepository.saveAndFlush(lectureInformation);
    }
}
