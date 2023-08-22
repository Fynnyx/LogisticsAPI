package ch.fwesterath.logisticsapi.models.project;

import ch.fwesterath.logisticsapi.models.department.Department;
import ch.fwesterath.logisticsapi.models.transport.Transport;
import ch.fwesterath.logisticsapi.models.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
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

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdAt;

    @ManyToMany(mappedBy = "projects", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    @JsonIgnoreProperties(value = "projects", allowSetters = true)
    private Set<User> users = Set.of();

    @ManyToOne(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    @JoinColumn(name = "owner_id", nullable = false)
    @JsonIgnoreProperties(value = {"ownedProjects", "projects"}, allowSetters = true)
    private User owner;

    @OneToMany(mappedBy = "project", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    @JsonIgnoreProperties(value = "project", allowSetters = true)
    private Set<Transport> transports = Set.of();

    @OneToMany(mappedBy = "project", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    @JsonIgnoreProperties(value = "project", allowSetters = true)
    private Set<Department> departments = Set.of();
}
