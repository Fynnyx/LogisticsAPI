package ch.fwesterath.logisticsapi.auth.jwt;

import ch.fwesterath.logisticsapi.error.ApiExceptionResponse;
import ch.fwesterath.logisticsapi.error.ApiMessageResponse;
import ch.fwesterath.logisticsapi.models.user.User;
import ch.fwesterath.logisticsapi.models.user.UserRepository;
import ch.fwesterath.logisticsapi.models.user.UserService;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class JwtAuthenticationFilter extends BasicAuthenticationFilter {
    UserService userService;
    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, UserService userService) {
        super(authenticationManager);
        this.userService = userService;
    }


    @Value("${jwt.secret}")
    private String secret = "a758c1be-db6b-473e-953b-b3db87715805a758c1be-db6b-473e-953b-b3db87715805";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("doFilterInternal");
        String header = request.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }
        String token = header.replace("Bearer ", "");
        System.out.println("token: " + token);
        Claims claims;
        try {
            claims = Jwts.parserBuilder().setSigningKey(secret.getBytes()).build().parseClaimsJws(token).getBody();
        } catch (SignatureException e) {
            throw new ApiExceptionResponse(HttpStatus.UNAUTHORIZED, "Token invalid");
        }
        System.out.println("claims: " + claims);
        String username = claims.getSubject();
        System.out.println("username: " + username);

//        Check if the token is expired
        Date expiration = claims.getExpiration();
        System.out.println("expiration: " + expiration);
        Date now = new Date();
        if (expiration.before(now)) {
            throw new ApiExceptionResponse(HttpStatus.UNAUTHORIZED, "Token expired");
        }
        if (username == null || !userService.existsByUsername(username)) {
            throw new ApiExceptionResponse(HttpStatus.UNAUTHORIZED, "Token invalid");
        }
        System.out.println("Authentication successful");
        User user = userService.getUserByUsername(username);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, user.getPasswordHash(), new ArrayList<>());

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        chain.doFilter(request, response);
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        return authentication -> {
            System.out.println("Authenticating user: " + authentication.getName());
            return authentication;
        };
    }
}

