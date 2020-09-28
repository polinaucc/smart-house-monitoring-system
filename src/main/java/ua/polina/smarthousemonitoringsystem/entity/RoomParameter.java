package ua.polina.smarthousemonitoringsystem.entity;

import javax.persistence.*;

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
