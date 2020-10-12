package ua.polina.smart_house_monitoring_system.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

/**
 * The entity for house presentation.
 *
 * @author Polina Serhiienko
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
    private Double size;

    @Column(name = "amount_of_rooms")
    private Integer amountOfRooms;

    @JsonIgnore
    @OneToMany(mappedBy = "house")
    private List<Room> rooms;

    @JsonIgnore
    @OneToMany(mappedBy = "house")
    private List<Resident> residents;

    @Override
    public String toString() {
        return "House{" +
                "id=" + id +
                ", size=" + size +
                ", amountOfRooms=" + amountOfRooms +
                '}';
    }
}
