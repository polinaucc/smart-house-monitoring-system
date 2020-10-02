package ua.polina.smart_house_monitoring_system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.polina.smart_house_monitoring_system.dto.HouseDto;
import ua.polina.smart_house_monitoring_system.entity.Address;
import ua.polina.smart_house_monitoring_system.entity.House;
import ua.polina.smart_house_monitoring_system.exception.DataExistsException;
import ua.polina.smart_house_monitoring_system.repository.AddressRepository;
import ua.polina.smart_house_monitoring_system.repository.HouseRepository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * The House service implementation.
 */
@Service
public class HouseServiceImpl implements HouseService {
    /**
     * The House repository.
     */
    private HouseRepository houseRepository;
    /**
     * The Address repository.
     */
    private AddressRepository addressRepository;

    /**
     * Instantiates a new House service.
     *
     * @param houseRepository   the house repository
     * @param addressRepository the address repository
     */
    @Autowired
    public HouseServiceImpl(HouseRepository houseRepository,
                            AddressRepository addressRepository) {
        this.houseRepository = houseRepository;
        this.addressRepository = addressRepository;
    }

    /**
     * Adds to database information about the house from the form
     * if address is successfully form.
     *
     * @param houseDto the house dto from the form
     * @return The house that is saved to database
     */
    @Transactional
    @Override
    public House addNewHouse(HouseDto houseDto) {
        Address address = saveAddress(houseDto);
        House house = House.builder()
                .address(address)
                .amountOfRooms(houseDto.getAmountOfRooms())
                .build();

        return houseRepository.save(house);
    }

    /**
     * Saves new address to the database with information from the form.
     *
     * @param houseDto the house dto from the form
     * @return The address that is saved to dataBase
     * @throws DataExistsException if the address with entered parameters
     *                             already exists in database
     */
    private Address saveAddress(HouseDto houseDto) {
        Address address = Address.builder()
                .country(houseDto.getCountry())
                .city(houseDto.getCity())
                .street(houseDto.getStreet())
                .houseNumber(houseDto.getHouseNumber())
                .flatNumber(houseDto.getFlatNumber())
                .build();
        if (addressRepository.
                existsAddressByCountryAndCityAndStreetAndHouseNumberAndFlatNumber(
                        address.getCountry(), address.getCity(),
                        address.getStreet(), address.getHouseNumber(),
                        address.getFlatNumber()
                )) {
            throw new DataExistsException("address.already.exists");
        }
        return addressRepository.save(address);
    }

    /**
     * Gets all houses in the database.
     *
     * @return the list of houses in the database
     */
    @Override
    public List<House> getAllHouses() {
        return houseRepository.findAll();
    }


    /**
     * Finds a house by its id.
     *
     * @param id the house unique id
     * @return the house, if id exists, otherwise exception
     * @throws IllegalArgumentException if the id doesn't exist
     */
    @Override
    public House getById(Long id) {
        return houseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "no.house.with.id"));
    }

    @Override
    public House updateSize(House house, Double newSize) {
        house.setSize(newSize);
        return houseRepository.save(house);
    }

}
