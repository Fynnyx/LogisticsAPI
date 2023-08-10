package ch.fwesterath.logisticsapi.models.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.io.Serializable;

@Entity
@Table(name = "user")
@Getter
@Setter
@ToString
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", length = 50, unique = true, nullable = false)
    private String username;

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
}
