package kr.co.knowledgerally.batch.core.job;

import org.quartz.InterruptableJob;
import org.springframework.scheduling.quartz.QuartzJobBean;

public abstract class KnowllyQuartzJob extends QuartzJobBean implements InterruptableJob {
}
