package ch.fwesterath.logisticsapi.auth.authentication;


import ch.fwesterath.logisticsapi.auth.dao.JwtAuthenticationResponse;
import ch.fwesterath.logisticsapi.auth.dao.SignUpRequest;
import ch.fwesterath.logisticsapi.auth.dao.SigninRequest;

public interface AuthenticationService {
    JwtAuthenticationResponse signup(SignUpRequest request);

    JwtAuthenticationResponse signin(SigninRequest request);
}