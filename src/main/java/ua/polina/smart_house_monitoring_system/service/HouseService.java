package ua.polina.smart_house_monitoring_system.service;

import ua.polina.smart_house_monitoring_system.dto.HouseDto;
import ua.polina.smart_house_monitoring_system.entity.House;

public interface HouseService {
    House addNewHouse(HouseDto houseDto);
}
