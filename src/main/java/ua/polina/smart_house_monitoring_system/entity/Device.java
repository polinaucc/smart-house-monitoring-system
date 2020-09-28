package ua.polina.smart_house_monitoring_system.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "device")
public class Device {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "state", nullable = false)
    @Enumerated(EnumType.STRING)
    private State state;

    @Column(name = "on_time")
    private LocalTime on_time;

    @Column(name = "off_time")
    private LocalTime off_time;
}