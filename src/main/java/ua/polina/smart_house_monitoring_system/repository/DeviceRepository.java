package ua.polina.smart_house_monitoring_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.polina.smart_house_monitoring_system.entity.Device;

/**
 * The Device repository.
 */
public interface DeviceRepository extends JpaRepository<Device, Long> {
    /**
     * Checks if a device with such a name exists.
     *
     * @param name the name
     * @return true if a device with such a name exists,
     * otherwise - false.
     */
    Boolean existsByName(String name);
}
