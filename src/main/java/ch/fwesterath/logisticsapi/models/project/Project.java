package ch.fwesterath.logisticsapi.models.project;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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

    @Column(name = "description")
    private String description;
}
