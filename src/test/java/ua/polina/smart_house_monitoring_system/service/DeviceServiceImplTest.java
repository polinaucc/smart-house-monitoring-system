package ua.polina.smart_house_monitoring_system.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ua.polina.smart_house_monitoring_system.dto.DeviceDto;
import ua.polina.smart_house_monitoring_system.dto.DeviceUserDto;
import ua.polina.smart_house_monitoring_system.entity.Device;
import ua.polina.smart_house_monitoring_system.entity.DeviceRoom;
import ua.polina.smart_house_monitoring_system.entity.Room;
import ua.polina.smart_house_monitoring_system.entity.State;
import ua.polina.smart_house_monitoring_system.repository.DeviceRepository;
import ua.polina.smart_house_monitoring_system.repository.DeviceRoomRepository;
import ua.polina.smart_house_monitoring_system.service.impl.DeviceServiceImpl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DeviceServiceImplTest {
    private static final long DEFAULT_ID = 1L;

    @Mock
    private DeviceRepository deviceRepository;
    @Mock
    private DeviceRoomRepository deviceRoomRepository;

    @InjectMocks
    private DeviceServiceImpl deviceService;

    private DeviceDto deviceDto;
    private Device device;

    @Before
    public void setUp() throws Exception {
        deviceDto = new DeviceDto();
        deviceDto.setName("Холодильник");

        device = new Device();
        device.setName(deviceDto.getName());
    }

    @Test
    public void saveDevice() {
        when(deviceRepository.existsByName(eq(deviceDto.getName()))).thenReturn(false);
        deviceService.saveDevice(deviceDto);
        verify(deviceRepository, times(1)).save(eq(device));
    }

    @Test
    public void testSaveDevice() {
        Room room = new Room();
        room.setName("Гостинная");

        DeviceUserDto deviceUserDto = new DeviceUserDto();
        deviceUserDto.setDeviceId(DEFAULT_ID);
        when(deviceRepository.findById(DEFAULT_ID)).thenReturn(Optional.of(device));
        DeviceRoom expectedDeviceRoom = DeviceRoom.builder()
                .room(room)
                .device(device)
                .state(State.OFF)
                .build();
        deviceService.saveDevice(deviceUserDto, room);
        verify(deviceRoomRepository, times(1)).save(eq(expectedDeviceRoom));
    }

    @Test
    public void getDevicesByRoom() {
        Room room = new Room();
        DeviceRoom deviceRoom = DeviceRoom.builder()
                .room(room)
                .device(device)
                .state(State.ON)
                .build();
        List<DeviceRoom> expectedList = Collections.singletonList(deviceRoom);
        when(deviceRoomRepository.findDeviceRoomByRoom(room)).thenReturn(Collections.singletonList(deviceRoom));
        List<DeviceRoom> actualList = deviceService.getDevicesByRoom(room);
        Assert.assertEquals(expectedList, actualList);
    }

    @Test
    public void getAllDevices() {
        deviceService.getAllDevices();
        verify(deviceRepository, times(1)).findAll();
    }

    @Test
    public void getDeviceById() {
        when(deviceRepository.findById(eq(DEFAULT_ID))).thenReturn(Optional.of(device));
        Device actualDevice = deviceService.getDeviceById(DEFAULT_ID);
        Assert.assertEquals(device, actualDevice);
    }

    @Test
    public void getDeviceRoomByRoomAndDevice() {
        Room room = new Room();
        DeviceRoom deviceRoom = DeviceRoom.builder()
                .room(room)
                .device(device)
                .state(State.ON)
                .build();
        List<DeviceRoom> expectedList = Collections.singletonList(deviceRoom);
        when(deviceRoomRepository.findDeviceRoomByRoomAndDevice(eq(room), eq(device))).thenReturn(expectedList);
        List<DeviceRoom> actualList = deviceService.getDeviceRoomByRoomAndDevice(room, device);
        Assert.assertEquals(expectedList, actualList);
    }

    @Test
    public void getDeviceRoomById() {
        Room room = new Room();
        DeviceRoom deviceRoom = DeviceRoom.builder()
                .id(DEFAULT_ID)
                .room(room)
                .device(device)
                .state(State.ON)
                .build();

        when(deviceRoomRepository.findById(eq(DEFAULT_ID))).thenReturn(Optional.of(deviceRoom));
        DeviceRoom actualDeviceRoom = deviceService.getDeviceRoomById(DEFAULT_ID);
        Assert.assertEquals(deviceRoom, actualDeviceRoom);
    }
}