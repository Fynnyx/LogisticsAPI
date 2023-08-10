package ch.fwesterath.logisticsapi.models.transport;

import ch.fwesterath.logisticsapi.models.item.Item;
import ch.fwesterath.logisticsapi.models.project.Project;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "transport")
@Getter
@Setter
@ToString
public class Transport {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "deliveryDate")
    private LocalDate deliveryDate;

    @Column(name = "isExternal", nullable = false)
    private boolean isExternal = false;

    @Column(name = "isApproved", nullable = false)
    private boolean isApproved = false;

    @Column(name = "isCompleted", nullable = false)
    private boolean isCompleted = false;

    @OneToMany(mappedBy = "transport", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    @JsonIgnoreProperties(value = "transport", allowSetters = true)
    private Set<Item> items = Set.of();

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "project_id", referencedColumnName = "id", nullable = false)
    @JsonIgnoreProperties(value = "transports", allowSetters = true)
    private Project project;
}
