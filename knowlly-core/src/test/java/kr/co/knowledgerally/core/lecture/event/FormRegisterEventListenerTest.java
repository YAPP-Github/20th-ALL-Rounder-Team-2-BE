package kr.co.knowledgerally.core.lecture.event;

import kr.co.knowledgerally.core.core.component.InAppNotifier;
import kr.co.knowledgerally.core.core.component.Notifier;
import kr.co.knowledgerally.core.core.util.EventPublisherWrapper;
import kr.co.knowledgerally.core.lecture.entity.Form;
import kr.co.knowledgerally.core.lecture.util.TestFormEntityFactory;
import kr.co.knowledgerally.core.user.entity.Notification;
import kr.co.knowledgerally.core.user.entity.User;
import kr.co.knowledgerally.core.user.repository.BallHistoryRepository;
import kr.co.knowledgerally.core.user.repository.UserRepository;
import kr.co.knowledgerally.core.user.service.BallTransactionService;
import kr.co.knowledgerally.core.user.vo.BallTransactionVo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@SpringBootTest
@Import(EventPublisherWrapper.class)
class FormRegisterEventListenerTest {
    @Autowired
    EventPublisherWrapper eventPublisher;

    @MockBean
    BallTransactionService ballTransactionService;

    @MockBean
    InAppNotifier notifier;

    @Test
    void 볼_감소_테스트() throws Exception {
        ArgumentCaptor<BallTransactionVo> argumentCaptor = ArgumentCaptor.forClass(BallTransactionVo.class);
        doNothing().when(ballTransactionService).makeBallTransaction(argumentCaptor.capture());
        Form form = new TestFormEntityFactory().createEntity(1L);

        eventPublisher.publishEvent(new FormRegisterEvent(form));
        Thread.sleep(1000);

        verify(ballTransactionService, times(1)).makeBallTransaction(any());
        assertEquals(1, argumentCaptor.getValue().getTargetUserId());
        assertEquals(-1, argumentCaptor.getValue().getCount());
    }

    @Test
    void 알림_전송_테스트() throws Exception {
        ArgumentCaptor<Notification> argumentCaptor = ArgumentCaptor.forClass(Notification.class);
        doNothing().when(notifier).sendNotification(argumentCaptor.capture());
        Form form = new TestFormEntityFactory().createEntity(1L);

        eventPublisher.publishEvent(new FormRegisterEvent(form));
        Thread.sleep(1000);

        verify(notifier, times(1)).sendNotification(any());
        assertEquals("테스트1님, 클래스 매칭 신청이 들어왔습니다. 신청서 확인 후 응답해주세요. ",
                argumentCaptor.getValue().getContent());
    }
}