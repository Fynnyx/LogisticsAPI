package ch.fwesterath.logisticsapi.models.user;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserAuthService {
    UserDetailsService userDetailsService();
}
