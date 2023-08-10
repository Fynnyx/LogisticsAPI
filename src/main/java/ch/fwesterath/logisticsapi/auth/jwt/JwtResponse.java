package ch.fwesterath.logisticsapi.auth.jwt;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@ToString
public class JwtResponse {

    private String token;

    public JwtResponse(String token) {
        this.token = token;
    }
}
