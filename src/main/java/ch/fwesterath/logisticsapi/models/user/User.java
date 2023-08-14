package ch.fwesterath.logisticsapi.models.user;

import ch.fwesterath.logisticsapi.helper.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "user")
@Getter
@Setter
@ToString
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "firstname", length = 50, nullable = false)
    private String firstname;

    @Column(name = "lastname", length = 50, nullable = false)
    private String lastname;

    @Column(name = "username", length = 50, unique = true, nullable = false)
    private String username;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", length = 20, nullable = false)
    private Role role;

    @Column(name = "password", length = 100, nullable = false)
    @Setter(AccessLevel.NONE)
    @JsonIgnore
    private String passwordHash;

    @Transient
    @JsonIgnore
    private String password;

    public void setPasswordHash(String password) {
        // Hash the password using a secure hashing algorithm (e.g., BCrypt)
        this.passwordHash = BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public boolean verifyPassword(String password) {
        // Verify the provided password against the stored hash
        return BCrypt.checkpw(password, this.passwordHash);
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
