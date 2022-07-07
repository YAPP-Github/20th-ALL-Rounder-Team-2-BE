package kr.co.knowledgerally.api;

import kr.co.knowledgerally.ApiBasePackage;
import kr.co.knowledgerally.CoreBasePackage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@ServletComponentScan
@SpringBootApplication(scanBasePackageClasses = { CoreBasePackage.class, ApiBasePackage.class })
public class KnowllyApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(KnowllyApiApplication.class, args);
	}

}
