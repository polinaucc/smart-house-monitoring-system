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
import ua.polina.smart_house_monitoring_system.service.impl.RoomServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class RoomServiceImplTest {
    private static final Long DEFAULT_ID = 1L;

    @Mock
    private RoomRepository roomRepository;
    @InjectMocks
    private RoomServiceImpl roomService;
    private House house;
    private List<Room> rooms;
    private RoomDto roomDto;
    private Room room1;

    @Before
    public void setUp() throws Exception {
        Address address = Address.builder()
                .country("Украина")
                .city("Киев")
                .street("Металлистов")
                .houseNumber("27")
                .build();

        house = House.builder()
                .id(DEFAULT_ID)
                .address(address)
                .amountOfRooms(3)
                .build();

        roomDto = RoomDto.builder()
                .name("Гостинная")
                .roomSize(10.5)
                .build();

        room1 = Room.builder()
                .id(DEFAULT_ID)
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

    @Test(expected = IllegalArgumentException.class)
    public void saveRoomWithException() {
        when(roomRepository.countRoomsByHouse(eq(house))).thenReturn(3);
        roomService.saveRoom(roomDto, house);
    }

    @Test
    public void deleteById() {
        when(roomRepository.findById(DEFAULT_ID)).thenReturn(Optional.of(room1));
        roomService.deleteById(DEFAULT_ID);
        verify(roomRepository, times(1)).deleteById(DEFAULT_ID);
    }

    @Test(expected = IllegalArgumentException.class)
    public void deleteByIdWithException() {
        when(roomRepository.findById(DEFAULT_ID)).thenReturn(Optional.empty());
        roomService.deleteById(DEFAULT_ID);
    }

    @Test
    public void getById() {
        when(roomRepository.findById(DEFAULT_ID)).thenReturn(Optional.of(room1));
        Room actualRoom = roomService.getById(DEFAULT_ID);
        Assert.assertEquals(room1, actualRoom);
    }
}