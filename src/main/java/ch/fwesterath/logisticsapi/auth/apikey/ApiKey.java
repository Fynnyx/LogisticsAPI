package ch.fwesterath.logisticsapi.auth.apikey;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "api_key")
@Getter
@Setter
@ToString
public class ApiKey {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "api_key")
    private String key;

    @Column(name = "active")
    private boolean active;

}
