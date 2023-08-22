package ch.fwesterath.logisticsapi.auth.controller;

import ch.fwesterath.logisticsapi.auth.authentication.AuthenticationService;
import ch.fwesterath.logisticsapi.auth.dao.JwtAuthenticationResponse;
import ch.fwesterath.logisticsapi.auth.dao.SignUpRequest;
import ch.fwesterath.logisticsapi.auth.dao.SigninRequest;
import ch.fwesterath.logisticsapi.auth.jwt.JwtServiceImpl;
import ch.fwesterath.logisticsapi.error.ApiExceptionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    private final JwtServiceImpl jwtService;

    Logger logger = Logger.getLogger(AuthenticationController.class.getName());

//    @PostMapping("/token/validation")
//    public ResponseEntity<Boolean> validateToken(@RequestBody String token) {
//        try {
//            logger.info("Token validation request: " + token);
//            return ResponseEntity.ok(jwtService.isTokenValid(token));
//        } catch (Exception e) {
//            throw new ApiExceptionResponse(HttpStatus.BAD_REQUEST, e.getMessage());
//        }
//    }

    @PostMapping("/user/signup")
    public ResponseEntity<JwtAuthenticationResponse> signup(@RequestBody SignUpRequest request) {
        try {
            logger.info("Signup request: " + request.toString());
            return ResponseEntity.ok(authenticationService.signup(request));
        } catch (Exception e) {
            throw new ApiExceptionResponse(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @PostMapping("/user/signin")
    public ResponseEntity<JwtAuthenticationResponse> signin(@RequestBody SigninRequest request) {
        try {
            logger.info("Signin request: " + request.toString());
            return ResponseEntity.ok(authenticationService.signin(request));
        } catch (Exception e) {
            throw new ApiExceptionResponse(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }
}