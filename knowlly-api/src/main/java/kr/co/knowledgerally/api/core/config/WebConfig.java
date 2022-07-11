package kr.co.knowledgerally.api.core.config;

import kr.co.knowledgerally.api.core.component.RequestLogFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("*")
                .allowedMethods("*")
                .maxAge(3000);
    }

//    @Bean
//    public RequestLogFilter requestLogFilter(){
//        FilterRegistrationBean<RequestLogFilter> registrationBean = new FilterRegistrationBean<>();
//        registrationBean.setFilter(new RequestLogFilter());
//        registrationBean.addUrlPatterns("/api/*");
//        registrationBean.setOrder(1);
//        registrationBean.setName("request-log-filter");
//        return registrationBean.getFilter();
//        return new RequestLogFilter();
//    }
}
