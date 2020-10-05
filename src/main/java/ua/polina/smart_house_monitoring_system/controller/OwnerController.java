package ua.polina.smart_house_monitoring_system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.polina.smart_house_monitoring_system.dto.DeviceUserDto;
import ua.polina.smart_house_monitoring_system.entity.*;
import ua.polina.smart_house_monitoring_system.service.DeviceParameterService;
import ua.polina.smart_house_monitoring_system.service.DeviceService;
import ua.polina.smart_house_monitoring_system.service.RoomService;
import ua.polina.smart_house_monitoring_system.service.UserService;

import java.util.List;

@Controller
@SessionAttributes("room")
@RequestMapping("/owner")
public class OwnerController {
    private final UserService userService;
    private final RoomService roomService;
    private final DeviceService deviceService;
    private final DeviceParameterService deviceParameterService;

    @Autowired
    public OwnerController(UserService userService, RoomService roomService,
                           DeviceService deviceService,
                           DeviceParameterService deviceParameterService) {
        this.userService = userService;
        this.roomService = roomService;
        this.deviceService = deviceService;
        this.deviceParameterService = deviceParameterService;
    }

    @GetMapping("/my-rooms")
    public String getMyRooms(Model model, @CurrentUser User user) {
        try {
            Resident resident = userService.getResidentByUser(user);
            House myHouse = resident.getHouse();
            List<Room> myRooms = roomService.getRoomsByHouse(myHouse);
            model.addAttribute("rooms", myRooms);
            model.addAttribute("error", null);
            return "rooms";
        } catch (IllegalArgumentException ex) {
            model.addAttribute("error", ex.getMessage());
            return "index";
        }
    }

    @GetMapping("/my-devices/{room-id}")
    public String getDevicesInRoom(@PathVariable("room-id") Long roomId, Model model) {
        Room room = roomService.getById(roomId);
        model.addAttribute("room", room);
        List<Device> devices = deviceService.getDevicesByRoom(room);
        model.addAttribute("devices", devices);
        model.addAttribute("error", null);
        return "devices";
    }

    @GetMapping("/add-device")
    public String getAddDeviceForm(Model model) {
        List<Device> devices = deviceService.getAllDevices();
        model.addAttribute("deviceList", devices);
        model.addAttribute("deviceDto", new DeviceUserDto());
        model.addAttribute("error", null);
        return "client/add-device";
    }

    @PostMapping("/add-device")
    public String addDevice(@ModelAttribute("deviceDto") DeviceUserDto deviceUserDto,
                            @ModelAttribute("room") Room room, Model model) {
        try {
            deviceService.saveDevice(deviceUserDto, room);
            return "redirect:/owner/my-devices/" + room.getId();
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "client/add-device";
        }
    }

    @GetMapping("/get-parameters/{device-id}")
    public String getDeviceParameters(@PathVariable("device-id") Long deviceId,
                                      @ModelAttribute("room") Room room, Model model) {
        try {
            Device device = deviceService.getDeviceById(deviceId);
            List<DeviceRoom> deviceRooms = deviceService.getDeviceRoomByRoomAndDevice(room, device);
            List<DeviceParameter> deviceParameters
                    = deviceParameterService.getDeviceParametersByDeviceRoom(deviceRooms);
            model.addAttribute("deviceParameters", deviceParameters);
            return "client/device-parameters";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "client/device-parameters";
        }
    }

    @GetMapping("/index")
    public String getIndexPage() {
        return "index";
    }


}
