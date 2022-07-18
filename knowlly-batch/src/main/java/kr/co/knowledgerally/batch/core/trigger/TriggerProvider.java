package kr.co.knowledgerally.batch.core.trigger;

import kr.co.knowledgerally.batch.core.job.JobRequest;
import org.quartz.Trigger;

public interface TriggerProvider {
    Trigger getTrigger(JobRequest jobRequest);
}
