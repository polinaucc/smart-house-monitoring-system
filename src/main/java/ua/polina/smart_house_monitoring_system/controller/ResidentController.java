package ua.polina.smart_house_monitoring_system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import ua.polina.smart_house_monitoring_system.api.EmergencyData;
import ua.polina.smart_house_monitoring_system.api.ResponseOnApi;
import ua.polina.smart_house_monitoring_system.api.RoomParametersApi;
import ua.polina.smart_house_monitoring_system.dto.RoomParameterDto;
import ua.polina.smart_house_monitoring_system.dto.SetUpParameterDto;
import ua.polina.smart_house_monitoring_system.entity.*;
import ua.polina.smart_house_monitoring_system.service.DeviceParameterService;
import ua.polina.smart_house_monitoring_system.service.DeviceService;
import ua.polina.smart_house_monitoring_system.service.RoomService;
import ua.polina.smart_house_monitoring_system.service.UserService;

import javax.validation.Valid;
import java.util.List;

/**
 * The Resident controller. It process residents' and some owners'
 * requests, since the owner has access to all residents' functions.
 */
@Controller
@RequestMapping(value = {"/resident", "/owner"})
@SessionAttributes(value = {"room", "deviceRoom", "fire"})
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
     * The device parameter sevice.
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
            if (EmergencyData.getInstance() != null)
                model.addAttribute("emergencies", EmergencyData.getInstance().messageList.getMessages());
            else model.addAttribute("emergencies", null);
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
            List<DeviceRoom> deviceRoomList =
                    deviceService.getDevicesByRoom(room);
            model.addAttribute("deviceRooms", deviceRoomList);
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
                           @ModelAttribute("room") Room room, Model model) {
        final String uri = "http://localhost:8081/sensor/on-device/";
        RestTemplate restTemplate = new RestTemplate();
        ResponseOnApi response = restTemplate.getForObject(
                uri + deviceRoomId, ResponseOnApi.class);
        //TODO: how to pass model attribute to the page????
        if (!response.getIsSuccess()
                && response.getHttpStatus() == HttpStatus.OK) {
            model.addAttribute("error", "can.not.on.device");
        } else if (response.getHttpStatus() == HttpStatus.NOT_FOUND) {
            model.addAttribute("error", "no.parameter.to.on.device");
        }
        return "redirect:/resident/my-devices/" + room.getId();

    }

    @GetMapping("/off-device/{device-room-id}")
    public String offDevice(@PathVariable("device-room-id") Long deviceRoomId,
                            @ModelAttribute("room") Room room) {
        final String uri = "http://localhost:8081/sensor/off-device/";
        RestTemplate restTemplate = new RestTemplate();
        Boolean isSuccess = restTemplate.getForObject(
                uri + deviceRoomId, Boolean.class);
        return "redirect:/resident/my-devices/" + room.getId();
    }

    @GetMapping("/set-up-parameter/{device-room-id}")
    public String getSetParameterForm(@PathVariable("device-room-id") Long deviceRoomId,
                                      @ModelAttribute("room") Room room, Model model) {
        try {
            DeviceRoom deviceRoom = deviceService.getDeviceRoomById(deviceRoomId);
            model.addAttribute("deviceRoom", deviceRoom);
            List<DeviceParameter> deviceParameters = deviceParameterService.getDeviceParametersByDeviceRoom(deviceRoom);
            model.addAttribute("parameters", deviceParameters);
            model.addAttribute("setUpParameterDto", new SetUpParameterDto());
            return "/client/set-parameter";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "redirect:/resident/my-devices/" + room.getId();
        }
    }

    @PostMapping("/set-up-parameter")
    public String setUpParameterValue(@ModelAttribute("setUpParameterDto")
                                              SetUpParameterDto setUpParameterDto,
                                      @ModelAttribute("room") Room room, Model model) {
        final String uri = "http://localhost:8081/sensor/set-up-parameter-value";
        RestTemplate restTemplate = new RestTemplate();
        DeviceParameter dp = restTemplate.postForObject(uri, setUpParameterDto, DeviceParameter.class);
        if (dp != null) {
            return "redirect:/resident/my-devices/" + room.getId();
        } else {
            model.addAttribute("no.parameter.with.such.id");
            return "/client/set-parameter";
        }
    }

    @GetMapping("/set-up-room-parameters/{room-id}")
    public String getRoomParametersForm(@PathVariable("room-id") Long roomId, Model model) {
        try {
            Room room = roomService.getById(roomId);
            model.addAttribute("room", room);
            model.addAttribute("roomParametersDto", new RoomParameterDto());
            model.addAttribute("error", null);
            return "/client/set-up-room-parameters";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "client/set-up-room-parameters";
        }
    }

    @PostMapping("set-up-room-parameters")
    public String addRoomParameters(@Valid @ModelAttribute("roomParameterDto") RoomParameterDto roomParameterDto,
                                    @ModelAttribute("room") Room room, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "redirect:/resident/set-up-room-parameters/" + room.getId();
        }
        final String uri = "http://localhost:8081/sensor/set-up-room-parameters";
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.postForObject(uri, new RoomParametersApi(roomParameterDto, room), String.class);
        return "redirect:/resident/my-rooms";
    }

    @GetMapping("simulate-flood/{room-id}")
    public String simulateFlood(@PathVariable("room-id") Long roomId) {
        final String uri = "http://localhost:8081/sensor/simulate-flood/";
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(uri + roomId, String.class);
        return "redirect:/resident/my-rooms";
    }

    @GetMapping("simulate-open-window/{room-id}")
    public String simulateOpenWindow(@PathVariable("room-id") Long roomId) {
        final String uri = "http://localhost:8081/sensor/simulate-open-window/";
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(uri + roomId, String.class);
        return "redirect:/resident/my-rooms";
    }
}
