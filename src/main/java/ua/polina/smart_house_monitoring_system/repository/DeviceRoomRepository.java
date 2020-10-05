package ua.polina.smart_house_monitoring_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.polina.smart_house_monitoring_system.entity.DeviceRoom;
import ua.polina.smart_house_monitoring_system.entity.Room;

import java.util.List;

public interface DeviceRoomRepository extends JpaRepository<DeviceRoom, Long> {
    List<DeviceRoom> findDeviceRoomByRoom(Room room);
}
