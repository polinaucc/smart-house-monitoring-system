package ua.polina.smart_house_monitoring_system.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalTime;

/**
 * The entity for device presentation.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Entity
@Table(name = "device")
public class Device {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "state", nullable = false)
    @Enumerated(EnumType.STRING)
    private State state;

    @Column(name = "on_time")
    private LocalTime onTime;

    @Column(name = "off_time")
    private LocalTime offTime;
}
