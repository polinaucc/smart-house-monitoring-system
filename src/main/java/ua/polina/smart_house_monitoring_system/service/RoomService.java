package ua.polina.smart_house_monitoring_system.service;

import ua.polina.smart_house_monitoring_system.dto.RoomDto;
import ua.polina.smart_house_monitoring_system.entity.House;
import ua.polina.smart_house_monitoring_system.entity.Room;

import java.util.List;

public interface RoomService {
    List<Room> getRoomsByHouse(House house);
    Room saveRoom(RoomDto roomDto, House house);
}
