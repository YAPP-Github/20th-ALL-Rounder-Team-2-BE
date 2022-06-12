package kr.co.knowledgerally.api;

import kr.co.knowledgerally.ApiBasePackage;
import kr.co.knowledgerally.CoreBasePackage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootApplication(scanBasePackageClasses = { CoreBasePackage.class, ApiBasePackage.class })
public class KnowllyApiApplication {

	@PostConstruct
	public void initialize() {
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
	}

	public static void main(String[] args) {
		SpringApplication.run(KnowllyApiApplication.class, args);
	}

}
