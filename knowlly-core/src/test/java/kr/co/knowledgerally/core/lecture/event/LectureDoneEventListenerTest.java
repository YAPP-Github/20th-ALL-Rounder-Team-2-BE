package kr.co.knowledgerally.core.lecture.event;

import kr.co.knowledgerally.core.core.component.InAppNotifier;
import kr.co.knowledgerally.core.core.util.EventPublisherWrapper;
import kr.co.knowledgerally.core.lecture.entity.Form;
import kr.co.knowledgerally.core.lecture.entity.Lecture;
import kr.co.knowledgerally.core.lecture.util.TestFormEntityFactory;
import kr.co.knowledgerally.core.lecture.util.TestLectureEntityFactory;
import kr.co.knowledgerally.core.user.entity.Notification;
import kr.co.knowledgerally.core.user.service.BallTransactionService;
import kr.co.knowledgerally.core.user.vo.BallTransactionVo;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@SpringBootTest
@Import(EventPublisherWrapper.class)
class LectureDoneEventListenerTest {
    @Autowired
    EventPublisherWrapper eventPublisher;

    @MockBean
    BallTransactionService ballTransactionService;

    @MockBean
    InAppNotifier notifier;

    @Test
    void 볼_증가_테스트() throws Exception {
        ArgumentCaptor<BallTransactionVo> ballArgumentCaptor = ArgumentCaptor.forClass(BallTransactionVo.class);
        doNothing().when(ballTransactionService).makeBallTransaction(ballArgumentCaptor.capture());
        ArgumentCaptor<Notification> NotiArgumentCaptor = ArgumentCaptor.forClass(Notification.class);
        doNothing().when(notifier).sendNotification(NotiArgumentCaptor.capture());
        Lecture lecture = createDoneLecture();

        eventPublisher.publishEvent(new LectureDoneEvent(lecture));
        Thread.sleep(1000);

        verify(ballTransactionService, times(1)).makeBallTransaction(any());
        assertEquals(1, ballArgumentCaptor.getValue().getTargetUserId());
        assertEquals(1, ballArgumentCaptor.getValue().getCount());
        verify(notifier, times(2)).sendNotification(any());
    }

    @Test
    void 알림_전송_테스트() throws Exception {
        ArgumentCaptor<Notification> argumentCaptor = ArgumentCaptor.forClass(Notification.class);
        doNothing().when(notifier).sendNotification(argumentCaptor.capture());
        Lecture lecture = createDoneLecture();

        eventPublisher.publishEvent(new LectureDoneEvent(lecture));
        Thread.sleep(1000);

        verify(notifier, times(2)).sendNotification(any());
    }

    private Lecture createDoneLecture() {
        Lecture lecture = new TestLectureEntityFactory().createEntity(1L);
        lecture.setState(Lecture.State.DONE);
        lecture.setForms(List.of(new TestFormEntityFactory().createEntity(1L)));
        lecture.getForms().get(0).setState(Form.State.ACCEPT);
        return lecture;
    }

    @Test
    void 볼_증가_테스트_클래스_DONE_아님() throws Exception {
        Lecture lecture = new TestLectureEntityFactory().createEntity(1L);

        eventPublisher.publishEvent(new LectureDoneEvent(lecture));
        Thread.sleep(1000);

        verify(ballTransactionService, times(0)).makeBallTransaction(any());
        verify(notifier, times(0)).sendNotification(any());
    }

    @Test
    void 알림_전송_테스트_클래스_DONE_아님() throws Exception {
        Lecture lecture = new TestLectureEntityFactory().createEntity(1L);

        eventPublisher.publishEvent(new LectureDoneEvent(lecture));
        Thread.sleep(1000);

        verify(notifier, times(0)).sendNotification(any());
    }
}