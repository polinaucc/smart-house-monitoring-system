package ua.polina.smarthousemonitoringsystem.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column
    private Long id;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> authorities = new HashSet<>();

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

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
