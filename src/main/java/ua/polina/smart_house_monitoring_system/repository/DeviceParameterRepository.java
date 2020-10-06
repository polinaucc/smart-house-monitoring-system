package ua.polina.smart_house_monitoring_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.polina.smart_house_monitoring_system.entity.DeviceParameter;
import ua.polina.smart_house_monitoring_system.entity.DeviceRoom;

import java.util.List;

/**
 * DeviceParameter repository.
 */
public interface DeviceParameterRepository extends JpaRepository<DeviceParameter, Long> {
    /**
     * Finds deviceParameter by the roomDevice.
     *
     * @param deviceRoom the deviceRoom instance
     * @return the list of  device parameters
     */
    List<DeviceParameter> findByRoomDevice(DeviceRoom deviceRoom);
}
