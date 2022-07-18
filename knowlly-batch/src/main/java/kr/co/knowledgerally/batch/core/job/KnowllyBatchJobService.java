package kr.co.knowledgerally.batch.core.job;

public interface KnowllyBatchJobService {
    KnowllyJobLauncherProvider getProvider();
    JobRequestService getJobRequestService();
}
