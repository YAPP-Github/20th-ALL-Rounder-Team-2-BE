package kr.co.knowledgerally.batch.core.config;

import kr.co.knowledgerally.batch.core.job.KnowllyJobLauncher;
import kr.co.knowledgerally.batch.core.job.KnowllyJobLauncherProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.support.JobRegistryBeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.GenericApplicationContext;

import javax.annotation.PostConstruct;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class BatchConfig {
    private final GenericApplicationContext applicationContext;
    private final List<KnowllyJobLauncherProvider> knowllyJobLauncherProviders;

    @Bean
    public JobRegistryBeanPostProcessor jobRegistryBeanPostProcessor(JobRegistry jobRegistry) {
        JobRegistryBeanPostProcessor jobRegistryBeanPostProcessor = new JobRegistryBeanPostProcessor();
        jobRegistryBeanPostProcessor.setJobRegistry(jobRegistry);
        return jobRegistryBeanPostProcessor;
    }

    @PostConstruct
    public void init() {
        for (KnowllyJobLauncherProvider provider : knowllyJobLauncherProviders) {
            applicationContext.registerBean(provider.getJobLauncherName(), KnowllyJobLauncher.class, provider::getLauncher);
            applicationContext.registerBean(provider.getLauncher().getJob().getName(), Job.class, () -> provider.getLauncher().getJob());
        }
    }

}