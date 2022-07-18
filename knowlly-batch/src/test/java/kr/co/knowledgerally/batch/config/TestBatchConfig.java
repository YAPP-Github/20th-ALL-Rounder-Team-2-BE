package kr.co.knowledgerally.batch.config;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
//@EnableAutoConfiguration
@EnableBatchProcessing
@ComponentScan(basePackages = {"kr.co.knowledgerally.core", "kr.co.knowledgerally.batch.core"})
public class TestBatchConfig {

}