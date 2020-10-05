package ua.polina.smart_house_monitoring_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.polina.smart_house_monitoring_system.entity.Resident;
import ua.polina.smart_house_monitoring_system.entity.User;

import java.util.Optional;

/**
 * Resident repository.
 */
public interface ResidentRepository extends JpaRepository<Resident, Long> {
    /**
     * Checks if resident with such passport already exists.
     *
     * @param passport passport
     * @return true, if the resident exists, otherwise - false
     */
    Boolean existsResidentByPassport(String passport);

    /**
     * Finds the Resident by user.
     *
     * @param user the user
     * @return optional, that is empty if the resident by such
     * user doesn't exist.
     */
    Optional<Resident> findByUser(User user);
}
