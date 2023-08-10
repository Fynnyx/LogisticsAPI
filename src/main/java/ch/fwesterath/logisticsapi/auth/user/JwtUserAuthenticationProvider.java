package ch.fwesterath.logisticsapi.auth.user;

import ch.fwesterath.logisticsapi.error.ApiExceptionResponse;
import ch.fwesterath.logisticsapi.models.user.User;
import ch.fwesterath.logisticsapi.models.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class JwtUserAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserService userService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        User user = userService.getUserByUsername(username);

        if (user != null ) {
            if (user.verifyPassword(password)) {
                System.out.println("JwtUserAuthenticationProvider.authenticate: user.verifyPassword(password) == true");
                List<GrantedAuthority> authorities = new ArrayList<>();

                return new UsernamePasswordAuthenticationToken(username, password, authorities);
            } else {
                throw new ApiExceptionResponse(HttpStatus.UNAUTHORIZED, "Invalid credentials");
            }
        } else {
            throw new ApiExceptionResponse(HttpStatus.NOT_FOUND, "User not found");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
