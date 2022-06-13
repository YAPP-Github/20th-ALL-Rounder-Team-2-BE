package kr.co.knowledgerally.core.user.service;

import kr.co.knowledgerally.core.core.exception.ResourceNotFoundException;
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

    @Transactional
    public User saveUser(@Valid User newUser) {
        return userRepository.saveAndFlush(newUser);
    }

    @Transactional(readOnly = true)
    public boolean isMemberExistByIdentifier(String identifier) { return userRepository.existsByIdentifierAndIsActive(identifier, true); }

    @Transactional(readOnly = true)
    public User findByIdentifier(String identifier) { return userRepository.findByIdentifierAndIsActive(identifier, true)
            .orElseThrow(() -> new ResourceNotFoundException(NOT_EXIST_USER)); }

    @Transactional(readOnly = true)
    public User findById(Long memberId) { return userRepository.findById(memberId)
            .orElseThrow(() -> new ResourceNotFoundException(NOT_EXIST_USER)); }
}
