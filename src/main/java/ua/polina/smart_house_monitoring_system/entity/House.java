package ua.polina.smart_house_monitoring_system.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "house")
public class House {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne
    @JoinColumn(name="house")
    private Address address;

    @Column(name = "size")
    private Long size;

    @Column(name = "amount_of_rooms")
    private Integer amountOfRooms;

    @OneToMany(mappedBy = "house")
    private List<Room> rooms;

    @OneToMany(mappedBy = "house")
    private List<Resident> residents;
}
