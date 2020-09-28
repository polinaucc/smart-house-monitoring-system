package ua.polina.smarthousemonitoringsystem.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "room")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "room", nullable = false)
    private String name;

    @Column(name = "size")
    private Long size;

    @ManyToOne
    @JoinColumn(name = "house_id")
    private House house;

}
