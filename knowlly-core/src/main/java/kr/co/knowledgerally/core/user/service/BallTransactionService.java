package kr.co.knowledgerally.core.user.service;

import kr.co.knowledgerally.core.user.entity.BallHistory;
import kr.co.knowledgerally.core.user.entity.User;
import kr.co.knowledgerally.core.user.repository.BallHistoryRepository;
import kr.co.knowledgerally.core.user.repository.UserRepository;
import kr.co.knowledgerally.core.user.vo.BallTransactionVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class BallTransactionService {
    private final BallHistoryRepository ballHistoryRepository;
    private final UserService userService;
    private final UserRepository userRepository;

    @Transactional
    public void makeBallTransaction(BallTransactionVo ballTransactionVo) {
        User target = userService.findById(ballTransactionVo.getTargetUserId());
        target.ballIncrement(ballTransactionVo.getCount());
        userRepository.save(target);
        ballHistoryRepository.saveAndFlush(buildBallHistory(ballTransactionVo));
    }

    private BallHistory buildBallHistory(BallTransactionVo ballTransactionVo) {
        return BallHistory.builder()
                .user(userService.findById(ballTransactionVo.getTargetUserId()))
                .title(ballTransactionVo.getTitle())
                .content(ballTransactionVo.getContent())
                .count(ballTransactionVo.getCount())
                .build();
    }
}
