package ua.polina.smart_house_monitoring_system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import ua.polina.smart_house_monitoring_system.api.DeviceRoomListApi;
import ua.polina.smart_house_monitoring_system.entity.*;
import ua.polina.smart_house_monitoring_system.service.DeviceParameterService;
import ua.polina.smart_house_monitoring_system.service.DeviceService;
import ua.polina.smart_house_monitoring_system.service.RoomService;
import ua.polina.smart_house_monitoring_system.service.UserService;

import java.util.List;
import java.util.Objects;

/**
 * The Resident controller. It process residents' and some owners'
 * requests, since the owner has access to all residents' functions.
 */
@Controller
@RequestMapping(value = {"/resident", "/owner"})
@SessionAttributes(value = {"room", "deviceRoom"})
public class ResidentController {
    /**
     * The user service.
     */
    private final UserService userService;

    /**
     * The room service.
     */
    private final RoomService roomService;

    /**
     * The device service.
     */
    private final DeviceService deviceService;

    /**
     * The device parameter sevvice.
     */
    private final DeviceParameterService deviceParameterService;

    /**
     * Instantiates a new Resident controller.
     *
     * @param userService            the user service
     * @param roomService            the room service
     * @param deviceService          the device service
     * @param deviceParameterService the device parameter service
     */
    @Autowired
    public ResidentController(UserService userService, RoomService roomService,
                              DeviceService deviceService,
                              DeviceParameterService deviceParameterService) {
        this.userService = userService;
        this.roomService = roomService;
        this.deviceService = deviceService;
        this.deviceParameterService = deviceParameterService;
    }

    /**
     * Gets rooms of logged in user.
     *
     * @param model the model
     * @param user  the user
     * @return the page with the list of rooms in the house of logged in user
     * or index page because of exception.
     */
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

    /**
     * Gets devices in room.
     *
     * @param roomId the room id
     * @param model  the model
     * @return the devices in room
     */
    @GetMapping("/my-devices/{room-id}")
    public String getDevicesInRoom(@PathVariable("room-id") Long roomId,
                                   Model model) {
        try {
            Room room = roomService.getById(roomId);
            model.addAttribute("room", room);
            model.addAttribute("value", false);
            List<DeviceRoom> deviceRoomList = deviceService.getDevicesByRoom(room);
            model.addAttribute("deviceRooms", deviceRoomList);
            model.addAttribute("error", null);
            return "devices";
        } catch (IllegalArgumentException e) {
            //TODO: think what user should see if there is no room with such id
            return "index";
        }

    }

    /**
     * Gets the page with the list of device parameters.
     *
     * @param deviceRoomId the device room id
     * @param room         the room
     * @param model        the model
     * @return the page with the list of device parameters.
     */
    @GetMapping("/get-parameters/{device-room-id}")
    public String getDeviceParameters(@PathVariable("device-room-id")
                                              Long deviceRoomId,
                                      @ModelAttribute("room") Room room,
                                      Model model) {
        try {
            DeviceRoom deviceRoom = deviceService
                    .getDeviceRoomById(deviceRoomId);
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

    /**
     * Gets the index page.
     *
     * @return the index page
     */
    @GetMapping("/index")
    public String getIndexPage() {
        return "index";
    }

    @GetMapping("/on-device/{device-room-id}")
    public String onDevice(@PathVariable("device-room-id") Long deviceRoomId,
                           @ModelAttribute("room") Room room) {
        final String uri = "http://localhost:8081/sensor/on-device/";
        RestTemplate restTemplate = new RestTemplate();
        Boolean isSuccess = restTemplate.getForObject(uri + deviceRoomId, Boolean.class);
        return "redirect:/resident/my-devices/" + room.getId();
    }

    @GetMapping("/off-device/{device-room-id}")
    public String offDevice(@PathVariable("device-room-id") Long deviceRoomId,
                           @ModelAttribute("room") Room room) {
        final String uri = "http://localhost:8081/sensor/off-device/";
        RestTemplate restTemplate = new RestTemplate();
        Boolean isSuccess = restTemplate.getForObject(uri + deviceRoomId, Boolean.class);
        return "redirect:/resident/my-devices/" + room.getId();
    }

    @GetMapping("/get-on-devices")
    public String getOnDevices(@ModelAttribute("room") Room room, Model model) {
        final String uri = "http://localhost:8081/sensor/get-on-devices/";
        RestTemplate restTemplate = new RestTemplate();
        DeviceRoomListApi onDeviceRooms = restTemplate.getForObject(uri + room.getId(),
                DeviceRoomListApi.class);
        for (DeviceRoom d : Objects.requireNonNull(onDeviceRooms).getDeviceRoomList()) {
            System.out.println(d);
        }
        return "index";
    }

}
