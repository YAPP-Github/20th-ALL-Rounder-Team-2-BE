package kr.co.knowledgerally.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync(proxyTargetClass = true)
@EnableAspectJAutoProxy(proxyTargetClass = true)
@SpringBootApplication
public class KnowllyCoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(KnowllyCoreApplication.class, args);
	}

}
