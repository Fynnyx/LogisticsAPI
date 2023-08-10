package ch.fwesterath.logisticsapi.models.project;

import ch.fwesterath.logisticsapi.models.department.Department;
import ch.fwesterath.logisticsapi.models.transport.Transport;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;

@Entity
@Table(name = "project")
@Getter
@Setter
@ToString
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "keyName", nullable = false, unique = true)
    private String keyName;

    @Column(name = "name", nullable = false)
    private String name;

    @Lob
    @Column(name = "logo")
    @JsonIgnore
    private byte[] logo;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "project", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    @JsonIgnoreProperties(value = "project", allowSetters = true)
    private Set<Transport> transports = Set.of();

    @OneToMany(mappedBy = "project", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    @JsonIgnoreProperties(value = "project", allowSetters = true)
    private Set<Department> departments = Set.of();
}
