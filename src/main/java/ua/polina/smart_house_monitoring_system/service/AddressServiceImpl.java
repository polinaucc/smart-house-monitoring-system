package ua.polina.smart_house_monitoring_system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.polina.smart_house_monitoring_system.dto.HouseDto;
import ua.polina.smart_house_monitoring_system.entity.Address;
import ua.polina.smart_house_monitoring_system.exception.DataExistsException;
import ua.polina.smart_house_monitoring_system.repository.AddressRepository;

import java.util.List;

/**
 * the Address service implementation.
 */
@Service
public class AddressServiceImpl implements AddressService {
    /**
     * The address repository.
     */
    AddressRepository addressRepository;

    /**
     * Instantiates a new Address service.
     *
     * @param addressRepository the address repository.
     */
    @Autowired
    public AddressServiceImpl(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    /**
     * Saves new address to the database with information from the form.
     *
     * @param houseDto the house dto from the form
     * @return The address that is saved to dataBase
     * @throws DataExistsException if the address with entered parameters
     *                             already exists in database
     */
    @Override
    public Address saveAddress(HouseDto houseDto) {
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
     * Gets the list of all addresses in database.
     *
     * @return the list of all addresses.
     */
    @Override
    public List<Address> getAllAddresses() {
        return addressRepository.findAll();
    }
}
