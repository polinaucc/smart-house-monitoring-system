package ua.polina.smart_house_monitoring_system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.polina.smart_house_monitoring_system.dto.RoomDto;
import ua.polina.smart_house_monitoring_system.entity.House;
import ua.polina.smart_house_monitoring_system.entity.Room;
import ua.polina.smart_house_monitoring_system.repository.RoomRepository;

import java.util.List;

@Service
public class RoomServiceImpl implements RoomService {
    RoomRepository roomRepository;

    @Autowired
    public RoomServiceImpl(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Override
    public List<Room> getRoomsByHouse(House house) {
        return roomRepository.findRoomsByHouse(house);
    }

    @Override
    public Room saveRoom(RoomDto roomDto, House house) {
        Room room = Room.builder()
                .name(roomDto.getName())
                .size(roomDto.getSize())
                .house(house)
                .build();
        if (house.getAmountOfRooms().equals(roomRepository.countRoomsByHouse(house))) {
            throw new IllegalArgumentException("max.amount.of.rooms");
        }
        return roomRepository.save(room);
    }

    @Override
    public void deleteById(Long roomId) {
        if (roomRepository.findById(roomId).isPresent()) {
            roomRepository.deleteById(roomId);
        } else throw new IllegalArgumentException("resource.not.exists");

    }
}
