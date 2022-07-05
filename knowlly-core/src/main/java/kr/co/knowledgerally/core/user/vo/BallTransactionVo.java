package kr.co.knowledgerally.core.user.vo;

import kr.co.knowledgerally.core.user.entity.User;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@Builder
@EqualsAndHashCode
public class BallTransactionVo {
    /**
     * 볼 지급 대상자 id
     */
    private final Long targetUserId;

    /**
     * 볼 지급 이유 제목
     */
    private final String title;

    /**
     * 볼 지급 이유 내용
     */
    private final String content;

    /**
     * 볼 지급 증감값
     */
    private final int count;
}
