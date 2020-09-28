package ua.polina.smart_house_monitoring_system.entity;

import javax.persistence.*;

@Entity
@Table(name = "room_device")
public class RoomDevice {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_parameter_id")
    private RoomParameter roomParameter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "device_id")
    private Device device;

    @Column(name = "set_up_value")
    private Double setUpValue;
}
