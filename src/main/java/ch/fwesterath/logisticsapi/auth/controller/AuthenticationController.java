package ch.fwesterath.logisticsapi.auth.controller;

import ch.fwesterath.logisticsapi.auth.authentication.AuthenticationService;
import ch.fwesterath.logisticsapi.auth.dao.JwtAuthenticationResponse;
import ch.fwesterath.logisticsapi.auth.dao.SignUpRequest;
import ch.fwesterath.logisticsapi.auth.dao.SigninRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    @PostMapping("/user/signup")
    public ResponseEntity<JwtAuthenticationResponse> signup(@RequestBody SignUpRequest request) {
        return ResponseEntity.ok(authenticationService.signup(request));
    }

    @PostMapping("/user/signin")
    public ResponseEntity<JwtAuthenticationResponse> signin(@RequestBody SigninRequest request) {
        return ResponseEntity.ok(authenticationService.signin(request));
    }
}