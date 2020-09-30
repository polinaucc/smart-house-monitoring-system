package ua.polina.smart_house_monitoring_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.polina.smart_house_monitoring_system.entity.User;

import java.util.Optional;

/**
 * User repository.
 */
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Find user by username.
     *
     * @param username the username
     * @return the optional the user
     */
    Optional<User> findByUsername(String username);

    /**
     * Checks if user with such username exists in database.
     *
     * @param username username
     * @return true, if user with username exists, otherwise false
     */
    Boolean existsByUsername(String username);


}
