package ua.polina.smart_house_monitoring_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.polina.smart_house_monitoring_system.entity.House;
import ua.polina.smart_house_monitoring_system.entity.Room;

import java.util.List;

/**
 * Room repository.
 */
public interface RoomRepository extends JpaRepository<Room, Long> {
    /**
     * Finds rooms in the house.
     *
     * @param house the house
     * @return the list of rooms
     */
    List<Room> findRoomsByHouse(House house);

    /**
     * Counts the number of rooms in the house.
     *
     * @param house the house
     * @return the number of rooms
     */
    Integer countRoomsByHouse(House house);
}
