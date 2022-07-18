package kr.co.knowledgerally.batch.core.trigger;

import kr.co.knowledgerally.batch.core.job.CronJobRequest;
import kr.co.knowledgerally.batch.core.job.JobRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class TriggerProviderFactory {
    private final SimpleTriggerProvider simpleTriggerProvider;
    private final CronTriggerProvider cronTriggerProvider;

    public TriggerProvider getInstance(JobRequest jobRequest) {
        if (jobRequest instanceof CronJobRequest) {
            return this.cronTriggerProvider;
        }

        return this.simpleTriggerProvider;
    }
}
