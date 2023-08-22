package ch.fwesterath.logisticsapi.auth.authentication;

import ch.fwesterath.logisticsapi.auth.dao.JwtAuthenticationResponse;
import ch.fwesterath.logisticsapi.auth.dao.SignUpRequest;
import ch.fwesterath.logisticsapi.auth.dao.SigninRequest;
import ch.fwesterath.logisticsapi.auth.jwt.JwtService;
import ch.fwesterath.logisticsapi.helper.Role;
import ch.fwesterath.logisticsapi.models.user.User;
import ch.fwesterath.logisticsapi.models.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    @Override
    public JwtAuthenticationResponse signup(SignUpRequest request) {
        var user = new User();
        user.setFirstname(request.getFirstname());
        user.setLastname(request.getLastname());
        user.setUsername(request.getUsername());
        user.setPasswordHash(request.getPassword());
        user.setRole(Role.USER);
        userRepository.save(user);
        var jwt = jwtService.generateToken(user);
        return JwtAuthenticationResponse.builder().token(jwt).user(user).build();
    }

    @Override
    public JwtAuthenticationResponse signin(SigninRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        var user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("Invalid credentials"));
        if (!user.verifyPassword(request.getPassword())) {
            throw new IllegalArgumentException("Invalid credentials");
        }
        var jwt = jwtService.generateToken(user);
        return JwtAuthenticationResponse.builder().token(jwt).user(user).build();
    }
}
