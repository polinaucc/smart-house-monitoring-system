package ua.polina.smart_house_monitoring_system.service;

import ua.polina.smart_house_monitoring_system.dto.HouseDto;
import ua.polina.smart_house_monitoring_system.entity.House;
import ua.polina.smart_house_monitoring_system.entity.Room;

import java.util.List;

public interface HouseService {
    House addNewHouse(HouseDto houseDto);

    List<House> getAllHouses();

    House getById(Long id);

    House updateSize(House house, List<Room> roomsInHouse);

    House getHouseByAddressId(Long addressId);
}
