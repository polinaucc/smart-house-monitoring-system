package ua.polina.smart_house_monitoring_system.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * The entity for Device presentation.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "device_parameter")
public class DeviceParameter {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;
}
