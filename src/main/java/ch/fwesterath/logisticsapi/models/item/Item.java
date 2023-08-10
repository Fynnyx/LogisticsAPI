package ch.fwesterath.logisticsapi.models.item;

import ch.fwesterath.logisticsapi.models.department.Department;
import ch.fwesterath.logisticsapi.models.transport.Transport;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "item")
@Getter
@Setter
@ToString
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name", nullable = false )
    private String name;

//    Default is 1
    @Column(name = "amount", nullable = false )
    private int amount = 1;

    @Column(name = "weight", nullable = false )
    private double weight;

    @Column(name = "length", nullable = false )
    private double length;

    @Column(name = "width", nullable = false )
    private double width;

    @Column(name = "height", nullable = false )
    private double height;

    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    @JoinColumn(name = "transport_id", referencedColumnName = "id")
    private Transport transport;

    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    @JoinColumn(name = "department_id", referencedColumnName = "id")
    private Department department;
}
