package ch.fwesterath.logisticsapi.auth.config;

import ch.fwesterath.logisticsapi.auth.jwt.JwtAuthenticationFilter;
import ch.fwesterath.logisticsapi.helper.Role;
import ch.fwesterath.logisticsapi.models.user.UserAuthService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final UserAuthService userService;

    Logger logger = Logger.getLogger(SecurityConfiguration.class.getName());
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        System.out.println("Debug: Role name: " + Role.ADMIN.name());
        http.csrf(AbstractHttpConfigurer::disable)
                .cors(
                        cors -> cors.configurationSource(request -> {
                            var corsConfiguration = new CorsConfiguration();
                            corsConfiguration.setAllowedOrigins(List.of("*"));
                            corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
                            corsConfiguration.setAllowedHeaders(List.of("Content-Type", "Authorization"));
                            return corsConfiguration.applyPermitDefaultValues();
                        })
                )
                .authorizeHttpRequests(request -> request
                        .requestMatchers(
                                "/auth/user/**",
                                "/docs/**",
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/swagger-ui.html",
                                "/webjars/**"
                        ).permitAll()
                        .requestMatchers(
                                HttpMethod.POST,
                                "/projects"
                        ).hasAnyAuthority("role.USER", "role.ADMIN")
                        .requestMatchers(
                                "/user",
                                "/projects/byId/**",
                                "/projects/byKeyName/**",
                                "/projects/*/transports",
                                "/projects/*/departments"
                        ).hasAnyAuthority("role.USER", "role.ADMIN")
                        .requestMatchers(
                                "/projects",
                                "/projects/**",
                                "/users"
                        ).hasAuthority("role.ADMIN")
                        .anyRequest().authenticated())
                .sessionManagement(manager -> manager.sessionCreationPolicy(STATELESS))
                .authenticationProvider(authenticationProvider()).addFilterBefore(
                        jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(new PreAuthFilter(), JwtAuthenticationFilter.class);
        http.addFilterAfter(new PrintRolesFilter(), JwtAuthenticationFilter.class);
        http.exceptionHandling(
                exceptionHandling -> exceptionHandling
                        .authenticationEntryPoint(
                                (request, response, authException) -> {
                                    logger.info("Authentication failed: " + authException.getMessage());
                                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "The user could not be authenticated or authorized");
                                }
                        )
                        .accessDeniedHandler(
                                (request, response, accessDeniedException) -> {
                                    logger.info("Access denied: " + accessDeniedException.getMessage());
                                    response.sendError(HttpServletResponse.SC_FORBIDDEN, "This user/token is not authorized to access this resource");
                                }
                        )
        );
        return http.build();
    }

    public class PrintRolesFilter extends OncePerRequestFilter {
        @Override
        protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && authentication.isAuthenticated()) {
                Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
                List<String> roles = authorities.stream()
                        .map(GrantedAuthority::getAuthority)
                        .toList();

                logger.info("Authenticated User Roles: " + roles);
            }

            filterChain.doFilter(request, response);
        }
    }

    public class PreAuthFilter extends OncePerRequestFilter {
        @Override
        protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
            logger.info("Request: " + request.getMethod() + " " + request.getRequestURI() + " from " + request.getRemoteAddr());
            filterChain.doFilter(request, response);
        }
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userService.userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
            throws Exception {
        return config.getAuthenticationManager();
    }
}
