package ua.polina.smart_house_monitoring_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.polina.smart_house_monitoring_system.entity.DeviceParameter;
import ua.polina.smart_house_monitoring_system.entity.DeviceRoom;

import java.util.List;

public interface DeviceParameterRepository extends JpaRepository<DeviceParameter, Long> {
    List<DeviceParameter> findByRoomDevice(DeviceRoom deviceRoom);
}
