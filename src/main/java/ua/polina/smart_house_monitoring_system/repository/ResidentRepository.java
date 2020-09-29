package ua.polina.smart_house_monitoring_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.polina.smart_house_monitoring_system.entity.Resident;

/**
 * Resident repository.
 */
public interface ResidentRepository extends JpaRepository<Resident, Long> {
}
