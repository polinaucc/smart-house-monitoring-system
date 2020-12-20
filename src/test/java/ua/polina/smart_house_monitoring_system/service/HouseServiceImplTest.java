package ua.polina.smart_house_monitoring_system.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import ua.polina.smart_house_monitoring_system.dto.HouseDto;
import ua.polina.smart_house_monitoring_system.entity.Address;
import ua.polina.smart_house_monitoring_system.entity.House;
import ua.polina.smart_house_monitoring_system.entity.Room;
import ua.polina.smart_house_monitoring_system.repository.HouseRepository;
import ua.polina.smart_house_monitoring_system.service.impl.HouseServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class HouseServiceImplTest {
    private static final Long DEFAULT_ID = 1L;

    @Mock
    private AddressService addressService;
    @Mock
    private HouseRepository houseRepository;
    @InjectMocks
    private HouseServiceImpl houseService;
    private HouseDto houseDto;
    private House house;
    private List<Room> rooms;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        houseDto = HouseDto.builder()
                .country("Украина")
                .city("Киев")
                .street("Металлистов")
                .houseNumber("27")
                .amountOfRooms(3)
                .build();

        Address address = Address.builder()
                .country(houseDto.getCountry())
                .city(houseDto.getCity())
                .street(houseDto.getStreet())
                .houseNumber(houseDto.getHouseNumber())
                .build();

        house = House.builder()
                .id(DEFAULT_ID)
                .address(address)
                .amountOfRooms(3)
                .build();

        Room room1 = Room.builder()
                .id(DEFAULT_ID)
                .name("Гостинная")
                .size(10.5)
                .build();

        Room room2 = Room.builder()
                .id(2L)
                .name("Спальня")
                .size(20.0)
                .build();

        rooms = Arrays.asList(room1, room2);

    }

    @Test
    public void addNewHouse() {
        houseService.addNewHouse(houseDto);
        verify(addressService, times(1)).saveAddress(eq(houseDto));
        House expectedHouse = House.builder()
                .amountOfRooms(3)
                .build();
        verify(houseRepository, times(1)).save(eq(expectedHouse));
    }

    @Test
    public void getById() {
        House expectedHouse = house;
        when(houseRepository.findById(1L)).thenReturn(Optional.of(expectedHouse));
        House actualHouse = houseService.getById(1L);
        Assert.assertEquals(expectedHouse, actualHouse);
    }

    @Test
    public void getAllHouses() {
        houseService.getAllHouses();
        verify(houseRepository, times(1)).findAll();
    }

    @Test
    public void updateSize() {
        houseService.updateSize(house, rooms);
        verify(houseRepository, times(1)).save(eq(house));
    }

    @Test
    public void getHouseByAddressId() {
        when(houseRepository.findByAddress_Id(eq(DEFAULT_ID))).thenReturn(Optional.of(house));
        House actualHouse = houseService.getHouseByAddressId(DEFAULT_ID);
        Assert.assertEquals(actualHouse, house);
    }


}