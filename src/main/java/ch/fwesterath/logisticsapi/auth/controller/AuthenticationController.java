package ch.fwesterath.logisticsapi.auth.controller;

import ch.fwesterath.logisticsapi.auth.authentication.AuthenticationService;
import ch.fwesterath.logisticsapi.auth.dao.JwtAuthenticationResponse;
import ch.fwesterath.logisticsapi.auth.dao.SignUpRequest;
import ch.fwesterath.logisticsapi.auth.dao.SigninRequest;
import ch.fwesterath.logisticsapi.error.ApiExceptionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
        try {
            return ResponseEntity.ok(authenticationService.signup(request));
        } catch (Exception e) {
            throw new ApiExceptionResponse(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @PostMapping("/user/signin")
    public ResponseEntity<JwtAuthenticationResponse> signin(@RequestBody SigninRequest request) {
        try {
            return ResponseEntity.ok(authenticationService.signin(request));
        } catch (Exception e) {
            throw new ApiExceptionResponse(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }
}