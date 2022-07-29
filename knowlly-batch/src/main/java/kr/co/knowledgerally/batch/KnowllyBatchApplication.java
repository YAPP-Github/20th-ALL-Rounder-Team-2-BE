package kr.co.knowledgerally.batch;


import kr.co.knowledgerally.BatchBasePackage;
import kr.co.knowledgerally.CoreBasePackage;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@EnableBatchProcessing
@SpringBootApplication(scanBasePackageClasses = { CoreBasePackage.class, BatchBasePackage.class })
public class KnowllyBatchApplication {
	public static void main(String[] args) {
		SpringApplication.run(KnowllyBatchApplication.class, args);
	}

}
