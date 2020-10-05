package ua.polina.smart_house_monitoring_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.polina.smart_house_monitoring_system.entity.House;

import java.util.Optional;

/**
 * House repository.
 */
public interface HouseRepository extends JpaRepository<House, Long> {
    /**
     * Finds a house by address id if it exists.
     *
     * @param addressId the address id
     * @return the optional that is empty if the house doesn't exist
     */
    Optional<House> findByAddress_Id(Long addressId);
}
