package ua.polina.smart_house_monitoring_system.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ua.polina.smart_house_monitoring_system.dto.RoomDto;
import ua.polina.smart_house_monitoring_system.entity.Address;
import ua.polina.smart_house_monitoring_system.entity.House;
import ua.polina.smart_house_monitoring_system.entity.Room;
import ua.polina.smart_house_monitoring_system.repository.RoomRepository;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class RoomServiceImplTest {
    @Mock
    RoomRepository roomRepository;
    @InjectMocks
    RoomServiceImpl roomService;
    House house;
    List<Room> rooms;
    RoomDto roomDto;

    @Before
    public void setUp() throws Exception {
        Address address = Address.builder()
                .country("Украина")
                .city("Киев")
                .street("Металлистов")
                .houseNumber("27")
                .build();

        house = House.builder()
                .id(1L)
                .address(address)
                .amountOfRooms(3)
                .build();

        roomDto = RoomDto.builder()
                .name("Гостинная")
                .size(10.5)
                .build();

        Room room1 = Room.builder()
                .id(1L)
                .name("Гостинная")
                .size(10.5)
                .house(house)
                .build();

        Room room2 = Room.builder()
                .id(2L)
                .name("Спальня")
                .size(20.0)
                .house(house)
                .build();

        rooms = Arrays.asList(room1, room2);
    }

    @Test
    public void getRoomsByHouse() {
        List<Room> expectedRooms = rooms;
        when(roomRepository.findRoomsByHouse(house)).thenReturn(expectedRooms);
        List<Room> actualRooms = roomService.getRoomsByHouse(house);
        Assert.assertEquals(expectedRooms, actualRooms);
    }

    @Test
    public void saveRoom() {
        roomService.saveRoom(roomDto, house);
        verify(roomRepository, times(1)).save(any(Room.class));
    }
}