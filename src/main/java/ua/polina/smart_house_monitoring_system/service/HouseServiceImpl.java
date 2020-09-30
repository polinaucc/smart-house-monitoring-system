package ua.polina.smart_house_monitoring_system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.polina.smart_house_monitoring_system.dto.HouseDto;
import ua.polina.smart_house_monitoring_system.entity.Address;
import ua.polina.smart_house_monitoring_system.entity.House;
import ua.polina.smart_house_monitoring_system.repository.AddressRepository;
import ua.polina.smart_house_monitoring_system.repository.HouseRepository;

import javax.transaction.Transactional;

@Service
public class HouseServiceImpl implements HouseService {
    HouseRepository houseRepository;
    AddressRepository addressRepository;

    @Autowired
    public HouseServiceImpl(HouseRepository houseRepository,
                            AddressRepository addressRepository) {
        this.houseRepository = houseRepository;
        this.addressRepository = addressRepository;
    }

    @Transactional
    @Override
    public House addNewHouse(HouseDto houseDto) {
        Address address = saveAddress(houseDto);
        House house = House.builder()
                .address(address)
                .amountOfRooms(houseDto.getAmountOfRooms())
                .size(houseDto.getSize())
                .build();

        return houseRepository.save(house);
    }

    private Address saveAddress(HouseDto houseDto) {
        Address address = Address.builder()
                .country(houseDto.getCountry())
                .city(houseDto.getCity())
                .street(houseDto.getStreet())
                .houseNumber(houseDto.getHouseNumber())
                .flatNumber(houseDto.getFlatNumber())
                .build();

        return addressRepository.save(address);
    }
}
