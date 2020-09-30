package ua.polina.smart_house_monitoring_system.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

/**
 * The entity for house presentation.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "house")
public class House {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne
    @JoinColumn(name = "address", unique = true)
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
