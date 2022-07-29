package kr.co.knowledgerally.batch.lecture;

import kr.co.knowledgerally.batch.AbstractJobTest;
import kr.co.knowledgerally.batch.config.TestBatchConfig;
import kr.co.knowledgerally.core.core.component.DateFactory;
import kr.co.knowledgerally.core.lecture.entity.Lecture;
import kr.co.knowledgerally.core.lecture.repository.LectureRepository;
import kr.co.knowledgerally.core.user.entity.BallHistory;
import kr.co.knowledgerally.core.user.repository.BallHistoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {LectureDoneJobConductor.class, TestBatchConfig.class,
        LectureDoneService.class, LectureRepository.class, DateFactory.class })
class LectureDoneJobTest extends AbstractJobTest {
    @Autowired
    LectureRepository lectureRepository;

    @Autowired
    BallHistoryRepository ballHistoryRepository;

    @BeforeEach
    public void clearMetadata() {
        jobRepositoryTestUtils.removeJobExecutions();
    }

    @Test
    public void 클래스_완료_테스트() throws Exception {
        // given
        JobParameters jobParameters =
                jobLauncherTestUtils.getUniqueJobParameters();

        // when
        JobExecution jobExecution =
                jobLauncherTestUtils.launchJob(jobParameters);
        Thread.sleep(1000);

        // then
        assertEquals(ExitStatus.COMPLETED,
                jobExecution.getExitStatus());
        assertEquals(3, lectureRepository.findAll().stream()
                .filter(x -> x.getState() == Lecture.State.DONE).count());
        assertEquals(8, ballHistoryRepository.count());
    }
}