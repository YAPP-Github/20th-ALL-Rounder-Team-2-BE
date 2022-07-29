package kr.co.knowledgerally.batch.util;

import kr.co.knowledgerally.batch.core.job.KnowllyJobLauncher;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class JobLauncherUtil implements ApplicationContextAware {
    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        // TODO Auto-generated method stub
        context = applicationContext;
    }

    public static KnowllyJobLauncher getBeanName(String beanName) {
        return context.getBean(beanName, KnowllyJobLauncher.class);
    }
}

