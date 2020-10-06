package ua.polina.smart_house_monitoring_system.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * The entity for User presentation.
 *
 * @author Polina Serhiienko
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column
    private Long id;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> authorities = new HashSet<>();

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @OneToOne(mappedBy = "user")
    private Resident resident;

    /**
     * Indicates whether the user's account has expired.
     *
     * @return true - he user's account is valid (ie non-expired)
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Indicates whether the user is locked or unlocked.
     *
     * @return true - the user is not locked
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Indicates whether the user's credentials (password) has expired.
     *
     * @return true - the user's credentials ara valid, non-expired.
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }


    /**
     * Indicates whether the user is enabled or disabled.
     *
     * @return true - the user is enabled
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
