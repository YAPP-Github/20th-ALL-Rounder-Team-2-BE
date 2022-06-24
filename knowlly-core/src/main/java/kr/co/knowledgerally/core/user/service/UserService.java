package kr.co.knowledgerally.core.user.service;

import kr.co.knowledgerally.core.core.exception.ResourceNotFoundException;
import kr.co.knowledgerally.core.core.message.ErrorMessage;
import kr.co.knowledgerally.core.user.entity.User;
import kr.co.knowledgerally.core.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

import static kr.co.knowledgerally.core.core.message.ErrorMessage.NOT_EXIST_USER;

@Validated
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    /**
     * 사용자를 저장합니다.
     * @param user 저장하고자 하는 사용자 엔티티
     * @return 저장된 사용자 엔티티
     */
    @Transactional
    public User saveUser(@Valid User user) {
        return userRepository.saveAndFlush(user);
    }

    /**
     * 식별자로 사용자가 존재하는지 검사합니다.
     * @param identifier 조회하고자 하는 사용자 식별자
     * @return 사용자 엔티티
     */
    @Transactional(readOnly = true)
    public boolean isMemberExistByIdentifier(String identifier) { return userRepository.existsByIdentifierAndIsActive(identifier, true); }

    /**
     * 사용자 프로필을 식별자로 조회합니다.
     * @param identifier 조회하고자 하는 사용자 식별자
     * @return 사용자 엔티티
     * @throws ResourceNotFoundException 존재하지 않는 사용자 Id의 경우
     */
    @Transactional(readOnly = true)
    public User findByIdentifier(String identifier) throws ResourceNotFoundException { return userRepository.findByIdentifierAndIsActive(identifier, true)
            .orElseThrow(() -> new ResourceNotFoundException(NOT_EXIST_USER)); }

    /**
     * 사용자 프로필을 조회합니다.
     * @param userId 조회하고자 하는 사용자 Id
     * @return 사용자 엔티티
     * @throws ResourceNotFoundException 존재하지 않는 사용자 Id의 경우
     */
    @Transactional(readOnly = true)
    public User findById(Long userId) throws ResourceNotFoundException {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessage.NOT_EXIST_USER));
    }
}
