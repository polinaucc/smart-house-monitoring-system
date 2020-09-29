package ua.polina.smart_house_monitoring_system.entity;

import javax.persistence.*;

/**
 * The entity for Room parameter.
 */
@Entity
@Table(name = "room_paramteter")
public class RoomParameter {
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "value")
    private Double value;

    @ManyToOne
    private Room room;
}
