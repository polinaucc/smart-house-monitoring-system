package ua.polina.smart_house_monitoring_system.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


/**
 * The entity for Device presentation.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Entity
@Table(name = "device_parameter")
public class DeviceParameter {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_device_id")
    private DeviceRoom roomDevice;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "min_theor_value", nullable = false)
    private Double minTheoreticalValue;

    @Column(name = "max_theor_value", nullable = false)
    private Double maxTheoreticalValue;

    @Column(name = "value")
    private Double value;
}
