package kr.co.knowledgerally.batch.lecture;

import kr.co.knowledgerally.batch.core.job.AbstractJobConductor;
import kr.co.knowledgerally.batch.util.CronExpression;
import kr.co.knowledgerally.core.core.component.DateFactory;
import kr.co.knowledgerally.core.lecture.entity.Lecture;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@NoArgsConstructor
public class LectureDoneJobConductor extends AbstractJobConductor {
    public static final String LECTURE_DONE_JOB_NAME = "lectureDoneJob";
    public static final String LECTURE_DONE_JOB_LAUNCHER_NAME = LECTURE_DONE_JOB_NAME + "Launcher";
    public static final String LECTURE_DONE_JOB_CRON = CronExpression.EVERY_MINUTE;
    private static int CHUNK_SIZE = 1000;

    private LectureDoneService lectureDoneService;
    private DateFactory dateFactory;

    @Autowired
    public void setTodayQuestionSelectionService(LectureDoneService lectureDoneService) {
        this.lectureDoneService = lectureDoneService;
    }

    @Autowired
    public void setDateFactory(DateFactory dateFactory) {
        this.dateFactory = dateFactory;
    }

    @Override
    public String getJobLauncherName() {
        return LECTURE_DONE_JOB_LAUNCHER_NAME;
    }

    @Override
    protected String getJobName() {
        return LECTURE_DONE_JOB_NAME;
    }

    @Override
    protected String getCronExpression() {
        return LECTURE_DONE_JOB_CRON;
    }

    @Override
    protected Job getJob() {
        return jobBuilderFactory.get(getJobName())
                .start(lectureDoneStep())
                .build();
    }

    private Step lectureDoneStep() {
        return stepBuilderFactory.get("lectureDoneStep")
                .<Lecture, Lecture>chunk(CHUNK_SIZE)
                .reader(lectureDoneReader())
                .writer(lectureDoneWriter())
                .build();
    }

    private JpaPagingItemReader<Lecture> lectureDoneReader() {
        JpaPagingItemReader<Lecture> reader = new JpaPagingItemReader<>();

        reader.setQueryString("select distinct lec from Lecture lec " +
                "left join fetch lec.forms f left join fetch f.user " +
                "join fetch lec.lectureInformation li join fetch li.category join fetch li.coach co join fetch co.user " +
                "WHERE lec.isActive = true AND lec.state <> :state AND lec.endAt < :now ");
        Map<String, Object> parameterValues = new HashMap<>();
        parameterValues.put("now", dateFactory.getLocalDateTimeNow());
        parameterValues.put("state", Lecture.State.DONE);
        reader.setParameterValues(parameterValues);

        reader.setPageSize(CHUNK_SIZE);
        reader.setEntityManagerFactory(emf);
        reader.setName("lectureDoneReader");

        return reader;
    }

    private ItemWriter<Lecture> lectureDoneWriter() {
        return lectures -> {
            log.info(">>>>>>>>>>> Item Write");
            lectureDoneService.makeLecturesDone(new ArrayList<>(lectures));
        };
    }
}
