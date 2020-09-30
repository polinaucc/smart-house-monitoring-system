package ua.polina.smart_house_monitoring_system.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * The entity for Resident presentation.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Entity
@Table(name = "resident")
public class Resident {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne(optional = false, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "first_name_ru")
    private String firstNameRu;

    @Column(name = "middle_name", nullable = false)
    private String middleName;

    @Column(name = "middle_name_ru")
    private String middleNameRu;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "last_name_ru")
    private String lastNameRu;

    @Column(name = "passport", unique = true, nullable = false)
    private String passport;

    @Column(name = "birthday")
    private LocalDate birthday;

    @ManyToOne
    private House house;
}
