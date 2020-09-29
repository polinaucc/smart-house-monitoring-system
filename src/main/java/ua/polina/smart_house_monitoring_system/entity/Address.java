package ua.polina.smart_house_monitoring_system.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Entity for user address presentation.
 *
 * @author Polina Serhiienko
 */
@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "country")
    private String country;

    @Column(name = "city")
    private String city;

    @Column(name = "street")
    private String street;

    @Column(name = "house_number")
    private String houseNumber;

    @Column(name = "flat_number")
    private String flatNumber;

    @OneToOne(mappedBy = "address", cascade = CascadeType.ALL)
    private House house;
}
