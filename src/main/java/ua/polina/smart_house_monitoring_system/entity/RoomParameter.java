package ua.polina.smart_house_monitoring_system.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


/**
 * The entity for Room parameter.
 *
 * @author Polina Serhiienko
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Entity
@Table(name = "room_parameter")
public class RoomParameter {
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "temparature")
    private Double temperature;

    @Column(name = "humidity")
    private Double humidity;

    @Column(name = "smole_level")
    private Double smokeLevel;

    @Column(name = "waterLevel")
    private Double waterLevel;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "room_id", unique = true)
    private Room room;
}
