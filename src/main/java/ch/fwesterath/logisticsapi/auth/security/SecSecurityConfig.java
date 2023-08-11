package ch.fwesterath.logisticsapi.auth.security;

import ch.fwesterath.logisticsapi.auth.jwt.JwtAuthenticationFilter;
import ch.fwesterath.logisticsapi.auth.user.JwtUserAuthenticationProvider;
import ch.fwesterath.logisticsapi.models.user.User;
import ch.fwesterath.logisticsapi.models.user.UserService;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.web.SecurityFilterChain;

import java.io.IOException;

@Configuration
@EnableWebSecurity
public class SecSecurityConfig {

    @Autowired
    UserService userService;

    Logger logger = LoggerFactory.getLogger(SecSecurityConfig.class);

    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(
                        AbstractHttpConfigurer::disable
                )
                .formLogin(
                        AbstractHttpConfigurer::disable
                )
                .addFilter(new JwtAuthenticationFilter(authenticationManager(), userService))
                .authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests
                        .requestMatchers("/auth/**").permitAll()

                        .requestMatchers("/docs/**").permitAll()
                        .requestMatchers("/swagger-ui/**").permitAll()
                        .requestMatchers("/v3/api-docs/**").permitAll()
                        .requestMatchers("/swagger-ui.html").permitAll()
                        .requestMatchers("/webjars/**").permitAll()
                );

        return http.build();
    }

    private AuthenticationManager authenticationManager() {
        return authentication -> {
            logger.info("Authenticating user: " + authentication.getName());
            return authentication;
        };
    }
}