package kr.co.knowledgerally.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootApplication
public class KnowllyCoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(KnowllyCoreApplication.class, args);
	}

}
