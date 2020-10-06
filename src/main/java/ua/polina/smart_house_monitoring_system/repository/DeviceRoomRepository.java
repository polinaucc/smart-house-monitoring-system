package ua.polina.smart_house_monitoring_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.polina.smart_house_monitoring_system.entity.Device;
import ua.polina.smart_house_monitoring_system.entity.DeviceRoom;
import ua.polina.smart_house_monitoring_system.entity.Room;

import java.util.List;

/**
 * The Device room repository.
 */
public interface DeviceRoomRepository extends JpaRepository<DeviceRoom, Long> {
    /**
     * Finds a device room list by the room.
     *
     * @param room the room
     * @return the list of device rooms.
     */
    List<DeviceRoom> findDeviceRoomByRoom(Room room);

    /**
     * Find device room list by the room and the device.
     *
     * @param room   the room
     * @param device the device
     * @return the list of devices in the room
     */
    List<DeviceRoom> findDeviceRoomByRoomAndDevice(Room room, Device device);
}
