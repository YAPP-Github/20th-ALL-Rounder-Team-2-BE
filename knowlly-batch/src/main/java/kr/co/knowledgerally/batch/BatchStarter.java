package kr.co.knowledgerally.batch;

import kr.co.knowledgerally.batch.core.job.JobRequestService;
import kr.co.knowledgerally.batch.core.job.ScheduledJobFactory;
import kr.co.knowledgerally.batch.core.scheduler.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class BatchStarter implements ApplicationListener<ContextRefreshedEvent> {
    private final ScheduleService scheduleService;
    private final ScheduledJobFactory jobFactory;
    private final List<JobRequestService> jobRequestServices;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        for (JobRequestService service : jobRequestServices) {
            scheduleService.addJob(
                    jobFactory.buildScheduledJob(service.create())
            );
        }
    }
}
