package ua.polina.smart_house_monitoring_system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.polina.smart_house_monitoring_system.dto.RoomDto;
import ua.polina.smart_house_monitoring_system.entity.House;
import ua.polina.smart_house_monitoring_system.entity.Room;
import ua.polina.smart_house_monitoring_system.repository.RoomRepository;
import ua.polina.smart_house_monitoring_system.service.RoomService;

import java.util.List;

/**
 * The Room service.
 */
@Service
public class RoomServiceImpl implements RoomService {
    /**
     * The Room repository.
     */
    RoomRepository roomRepository;

    /**
     * Instantiates a new Room service.
     *
     * @param roomRepository the room repository
     */
    @Autowired
    public RoomServiceImpl(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    /**
     * Gets rooms by the house.
     *
     * @param house the house
     * @return the list of rooms
     */
    @Override
    public List<Room> getRoomsByHouse(House house) {
        return roomRepository.findRoomsByHouse(house);
    }

    /**
     * Saves a room.
     *
     * @param roomDto the room dto that has necessary information to save
     *                the room.
     * @param house   the house
     * @return saved room
     * @throws IllegalArgumentException if the number of rooms is more than
     *                                  number of rooms in the house
     */
    @Override
    public Room saveRoom(RoomDto roomDto, House house) {
        Room room = Room.builder()
                .name(roomDto.getName())
                .size(roomDto.getRoomSize())
                .house(house)
                .build();
        if (house.getAmountOfRooms().equals(roomRepository.countRoomsByHouse(house))) {
            throw new IllegalArgumentException("max.amount.of.rooms");
        }
        return roomRepository.save(room);
    }

    /**
     * Deletes the room by its id.
     *
     * @param roomId the room id
     */
    @Override
    public void deleteById(Long roomId) {
        if (roomRepository.findById(roomId).isPresent()) {
            roomRepository.deleteById(roomId);
        } else throw new IllegalArgumentException("resource.not.exists");

    }

    /**
     * Gets the room by its id.
     *
     * @param id the room id
     * @return the room
     */
    @Override
    public Room getById(Long id) {
        return roomRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("no.room.with.such.id"));
    }
}
