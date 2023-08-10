package ch.fwesterath.logisticsapi.auth.controller;

import ch.fwesterath.logisticsapi.auth.jwt.JwtProvider;
import ch.fwesterath.logisticsapi.auth.jwt.JwtResponse;
import ch.fwesterath.logisticsapi.auth.user.JwtUserAuthenticationProvider;
import ch.fwesterath.logisticsapi.error.ApiMessageResponse;
import ch.fwesterath.logisticsapi.models.user.User;
import ch.fwesterath.logisticsapi.models.user.UserService;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Date;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private JwtUserAuthenticationProvider jwtAuthenticationProvider;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private UserService userService;

    @PostMapping("/user")
    public ResponseEntity<?> authenticateUser(@RequestBody AuthRequest request) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());
        authentication = jwtAuthenticationProvider.authenticate(authentication);

        String token = jwtProvider.generateToken(request.getUsername());
        return ResponseEntity.ok(new JwtResponse(token));
    }

    @PostMapping("/user/register")
    public ResponseEntity<?> registerUser(@RequestBody AuthRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());

        userService.createUser(user);

        return ResponseEntity.ok(new ApiMessageResponse("User created succsessfully", HttpStatus.OK));
    }

    @Getter
    @Setter
    @ToString
    static class AuthRequest {
        private String username;
        private String password;
    }
}
