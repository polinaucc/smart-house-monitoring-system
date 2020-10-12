package ua.polina.smart_house_monitoring_system.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.List;

/**
 * The entity for presentation of device in the room.
 *
 * @author Polina Serhiienko
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Entity
@Table(name = "device_room")
public class DeviceRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @OnDelete(      action = OnDeleteAction.CASCADE)
    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    @ManyToOne
    @JoinColumn(name = "device_id")
    private Device device;

    @Column(name = "state", nullable = false)
    @Enumerated(EnumType.STRING)
    private State state;

    @Column(name = "on_time")
    private LocalTime onTime;

    @Column(name = "off_time")
    private LocalTime offTime;

    @OneToMany(mappedBy = "roomDevice", cascade = CascadeType.ALL)
    private List<DeviceParameter> deviceParameters;

    @Override
    public String toString() {
        return "DeviceRoom{" +
                "id=" + id +
                ", room=" + room +
                ", device=" + device +
                ", state=" + state +
                ", onTime=" + onTime +
                ", offTime=" + offTime +
                '}';
    }
}
