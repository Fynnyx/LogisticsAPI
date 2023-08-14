package ch.fwesterath.logisticsapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//@SpringBootApplication
@Configuration
@EnableAutoConfiguration
@ComponentScan
public class LogisticsAPIApplication {
    public static void main(String[] args) {
        SpringApplication.run(LogisticsAPIApplication.class, args);
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            @CrossOrigin(origins = "*")
            public void addCorsMappings(org.springframework.web.servlet.config.annotation.CorsRegistry registry) {
                registry.addMapping("/**").allowedMethods("*");
            }
        };
    }
}
