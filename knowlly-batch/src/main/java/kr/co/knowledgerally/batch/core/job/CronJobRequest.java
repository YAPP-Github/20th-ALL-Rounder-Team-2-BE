package kr.co.knowledgerally.batch.core.job;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter @Setter
public class CronJobRequest extends JobRequest {
    private final String cronExpression;
}
