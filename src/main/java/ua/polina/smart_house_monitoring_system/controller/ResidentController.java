package ua.polina.smart_house_monitoring_system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.polina.smart_house_monitoring_system.entity.*;
import ua.polina.smart_house_monitoring_system.service.DeviceParameterService;
import ua.polina.smart_house_monitoring_system.service.DeviceService;
import ua.polina.smart_house_monitoring_system.service.RoomService;
import ua.polina.smart_house_monitoring_system.service.UserService;

import java.util.List;

@Controller
@RequestMapping(value={"/resident", "/owner"})
@SessionAttributes(value = {"room", "deviceRoom"})
public class ResidentController {
    private final UserService userService;
    private final RoomService roomService;
    private final DeviceService deviceService;
    private final DeviceParameterService deviceParameterService;

    @Autowired
    public ResidentController(UserService userService, RoomService roomService,
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
        List<DeviceRoom> deviceRoomList = deviceService.getDevicesByRoom(room);
        model.addAttribute("deviceRooms", deviceRoomList);
        model.addAttribute("error", null);
        return "devices";
    }

    @GetMapping("/get-parameters/{device-room-id}")
    public String getDeviceParameters(@PathVariable("device-room-id") Long deviceRoomId,
                                      @ModelAttribute("room") Room room, Model model) {
        try {
            DeviceRoom deviceRoom = deviceService.getDeviceRoomById(deviceRoomId);
            model.addAttribute("deviceRoom", deviceRoom);
            List<DeviceParameter> deviceParameters
                    = deviceParameterService.getDeviceParametersByDeviceRoom(deviceRoom);
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
