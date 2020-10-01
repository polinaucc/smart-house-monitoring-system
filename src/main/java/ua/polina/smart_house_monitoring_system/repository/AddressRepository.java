package ua.polina.smart_house_monitoring_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.polina.smart_house_monitoring_system.entity.Address;

/**
 * Address repository.
 */
public interface AddressRepository extends JpaRepository<Address, Long> {
    /**
     * Checks if address with such country and city and street and house number
     * and flat number boolean.
     *
     * @param country the country
     * @param city    the city
     * @param street  the street
     * @param house   the house
     * @param flat    the flat
     * @return true, if the address exists, otherwise false
     */
    Boolean existsAddressByCountryAndCityAndStreetAndHouseNumberAndFlatNumber(
            String country, String city, String street,
            String house, String flat
    );
}
