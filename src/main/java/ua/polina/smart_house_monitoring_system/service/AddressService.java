package ua.polina.smart_house_monitoring_system.service;

import ua.polina.smart_house_monitoring_system.dto.HouseDto;
import ua.polina.smart_house_monitoring_system.entity.Address;

import java.util.List;

public interface AddressService {
    Address saveAddress(HouseDto houseDto);

    List<Address> getAllAddresses();
}
