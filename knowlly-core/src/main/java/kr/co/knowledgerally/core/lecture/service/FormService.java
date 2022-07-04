package kr.co.knowledgerally.core.lecture.service;

import kr.co.knowledgerally.core.core.exception.ResourceNotFoundException;
import kr.co.knowledgerally.core.core.message.ErrorMessage;
import kr.co.knowledgerally.core.lecture.entity.Category;
import kr.co.knowledgerally.core.lecture.entity.Form;
import kr.co.knowledgerally.core.lecture.repository.CategoryRepository;
import kr.co.knowledgerally.core.lecture.repository.FormRepository;
import kr.co.knowledgerally.core.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

@Validated
@Service
@RequiredArgsConstructor
public class FormService {
    private final FormRepository formRepository;

    /**
     * id로 신청서를 조회합니다.
     * @param id 신청서 id
     * @return 신청서 객체
     * @throws ResourceNotFoundException 해당하는 id가 존재하지 않을 때
     */
    @Transactional(readOnly = true)
    public Form findById(long id) throws ResourceNotFoundException {
        return formRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessage.NOT_EXIST_FORM));
    }

    /**
     * 사용자로 신청서 목록을 조회합니다.
     * @param user 사용자
     * @return 신청서 리스트
     */
    @Transactional(readOnly = true)
    public List<Form> findAllByUser(User user) {
        return formRepository.findAllByUserAndIsActiveOrderByCreatedAtDesc(user, true);
    }
}
