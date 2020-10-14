package ua.polina.smart_house_monitoring_system.service.impl;

import org.decimal4j.util.DoubleRounder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.polina.smart_house_monitoring_system.dto.HouseDto;
import ua.polina.smart_house_monitoring_system.entity.Address;
import ua.polina.smart_house_monitoring_system.entity.House;
import ua.polina.smart_house_monitoring_system.entity.Room;
import ua.polina.smart_house_monitoring_system.repository.HouseRepository;
import ua.polina.smart_house_monitoring_system.service.AddressService;
import ua.polina.smart_house_monitoring_system.service.HouseService;

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
    private AddressService addressService;

    /**
     * Instantiates a new House service.
     *
     * @param houseRepository the house repository
     * @param addressService  the address service
     */
    @Autowired
    public HouseServiceImpl(HouseRepository houseRepository,
                            AddressService addressService) {
        this.houseRepository = houseRepository;
        this.addressService = addressService;
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
        Address address = addressService.saveAddress(houseDto);
        House house = House.builder()
                .address(address)
                .amountOfRooms(houseDto.getAmountOfRooms())
                .build();

        return houseRepository.save(house);
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

    /**
     * Updates the size of house depends on room sizes in it.
     *
     * @param house        the house
     * @param roomsInHouse the list of rooms in the house
     * @return the house with new size value
     */
    @Override
    public House updateSize(House house, List<Room> roomsInHouse) {
        house.setSize(0.0);
        for (Room r : roomsInHouse) {
            house.setSize(DoubleRounder.round(
                    house.getSize() + r.getSize(), 2));
        }
        return houseRepository.save(house);
    }

    /**
     * Gets house by address id if the house exists.
     *
     * @param addressId the address id
     * @return the house with an address that matches an id,
     * otherwise - exception
     * @throws IllegalArgumentException if the house with such address
     *                                  doesn't exist
     */
    @Override
    public House getHouseByAddressId(Long addressId) {
        return houseRepository.findByAddress_Id(addressId)
                .orElseThrow(() -> new IllegalArgumentException("illegal.address"));
    }


}
