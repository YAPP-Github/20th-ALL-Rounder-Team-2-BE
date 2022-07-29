package kr.co.knowledgerally.core.lecture.event;

import kr.co.knowledgerally.core.core.component.InAppNotifier;
import kr.co.knowledgerally.core.core.util.EventPublisherWrapper;
import kr.co.knowledgerally.core.lecture.entity.Form;
import kr.co.knowledgerally.core.lecture.util.TestFormEntityFactory;
import kr.co.knowledgerally.core.user.entity.Notification;
import kr.co.knowledgerally.core.user.service.BallTransactionService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@Import(EventPublisherWrapper.class)
class FormStateChangedEventListenerTest {
    @Autowired
    EventPublisherWrapper eventPublisher;

    @MockBean
    InAppNotifier notifier;

    @Test
    void 알림_전송_테스트() throws Exception {
        ArgumentCaptor<Notification> argumentCaptor = ArgumentCaptor.forClass(Notification.class);
        doNothing().when(notifier).sendNotification(argumentCaptor.capture());
        Form form = new TestFormEntityFactory().createEntity(1L);

        eventPublisher.publishEvent(new FormStateChangedEvent(form));
        Thread.sleep(1000);

        verify(notifier, times(1)).sendNotification(any());
        assertEquals("테스트1님, 아쉽게도 신청한 매칭이 거절됐습니다. 새로운 클래스를 탐색해보고 매칭 신청을 해보세요.",
                argumentCaptor.getValue().getContent());
    }

    @Test
    void 알림_전송_테스트_ACCEPT() throws Exception {
        ArgumentCaptor<Notification> argumentCaptor = ArgumentCaptor.forClass(Notification.class);
        doNothing().when(notifier).sendNotification(argumentCaptor.capture());
        Form form = new TestFormEntityFactory().createEntity(1L);
        form.setState(Form.State.ACCEPT);

        eventPublisher.publishEvent(new FormStateChangedEvent(form));
        Thread.sleep(1000);

        verify(notifier, times(2)).sendNotification(any());
        assertEquals("테스트1님, 신청한 매칭이 수락되었습니다. 클래스 진행 장소 협의를 위해 코치의 연락처로 연락해주세요. ",
                argumentCaptor.getAllValues().get(0).getContent());
        assertEquals("테스트1님, 예정된 클래스의 진행 장소 협의를 위해 플레이어의 연락처로 연락해주세요. ",
                argumentCaptor.getAllValues().get(1).getContent());
    }
}