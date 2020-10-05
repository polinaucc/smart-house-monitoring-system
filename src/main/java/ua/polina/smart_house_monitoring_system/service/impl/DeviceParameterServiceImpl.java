package ua.polina.smart_house_monitoring_system.service.impl;

import org.springframework.stereotype.Service;
import ua.polina.smart_house_monitoring_system.entity.DeviceParameter;
import ua.polina.smart_house_monitoring_system.entity.DeviceRoom;
import ua.polina.smart_house_monitoring_system.repository.DeviceParameterRepository;
import ua.polina.smart_house_monitoring_system.service.DeviceParameterService;

import java.util.ArrayList;
import java.util.List;

@Service
public class DeviceParameterServiceImpl implements DeviceParameterService {
    DeviceParameterRepository deviceParameterRepository;

    public DeviceParameterServiceImpl(DeviceParameterRepository deviceParameterRepository) {
        this.deviceParameterRepository = deviceParameterRepository;
    }

    @Override
    public List<DeviceParameter> getDeviceParametersByDeviceRoom(List<DeviceRoom> deviceRooms) {
        List<DeviceParameter> deviceParameterList = new ArrayList<>();
        for (DeviceRoom dr : deviceRooms) {
            deviceParameterList.addAll(deviceParameterRepository.findByRoomDevice(dr));
        }
        return deviceParameterList;
    }
}
