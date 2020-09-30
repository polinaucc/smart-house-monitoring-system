package ua.polina.smart_house_monitoring_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.polina.smart_house_monitoring_system.entity.Resident;

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
}
