package ua.polina.smart_house_monitoring_system.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "device_parameter_value")
public class DeviceParameterValue {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "device_id")
    private Device device;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "device_parameter_id")
    private DeviceParameter deviceParameter;

    @Column(name = "min_theor_value", nullable = false)
    private Double minTheoreticalValue;

    @Column(name = "max_theor_value", nullable = false)
    private Double maxTheoreticalValue;

    @Column(name = "value")
    private Double value;
}
