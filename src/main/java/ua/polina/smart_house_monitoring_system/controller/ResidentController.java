package ua.polina.smart_house_monitoring_system.controller;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import ua.polina.smart_house_monitoring_system.api.EmergencyData;
import ua.polina.smart_house_monitoring_system.api.MessageList;
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
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * The Resident controller. It process residents' and some owners'
 * requests, since the owner has access to all residents' functions.
 */
@Controller
@RequestMapping(value = {"/resident", "/owner"})
@SessionAttributes(value = {"room", "deviceRoom", "house"})
public class ResidentController {
    /**
     * The Logger.
     */
    private static final Logger LOGGER = LogManager.getLogger(ResidentController.class);
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
     * The current house of the logged in user.
     */
    private House house;

    /**
     * The resource bundle for errors` localization.
     */
    private ResourceBundle rb;

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
        rb = ResourceBundle.getBundle(
                "messages", new Locale("en", "UK"));
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
            house = myHouse;
            model.addAttribute("house", myHouse);
            List<Room> myRooms = roomService.getRoomsByHouse(myHouse);
            model.addAttribute("rooms", myRooms);
            if (EmergencyData.getInstance() != null) {
                model.addAttribute("emergencies",
                        EmergencyData.getInstance().messageList.getMessages());
            } else {
                model.addAttribute("emergencies", null);
            }
            return "rooms";
        } catch (IllegalArgumentException ex) {
            LOGGER.error(user.getUsername() + rb.getString(ex.getMessage()));
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
            LOGGER.error(e.getMessage() + " " + roomId);
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
            List<DeviceParameter> deviceParameters = deviceParameterService
                    .getDeviceParametersByDeviceRoom(deviceRoom);
            model.addAttribute("deviceParameters", deviceParameters);
            return "client/device-parameters";
        } catch (IllegalArgumentException e) {
            LOGGER.error(e.getMessage() + deviceRoomId);
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

    /**
     * Turns on the device.
     *
     * @param deviceRoomId the device room id
     * @param room         the room
     * @param model        the model
     * @return the redirection to the page with device list
     */
    @GetMapping("/on-device/{device-room-id}")
    public String onDevice(@PathVariable("device-room-id") Long deviceRoomId,
                           @ModelAttribute("room") Room room, Model model) {
        final String uri = "http://localhost:8081/sensor/on-device/";
        RestTemplate restTemplate = new RestTemplate();
        ResponseOnApi response = restTemplate.getForObject(
                uri + deviceRoomId, ResponseOnApi.class);
        if (!Objects.requireNonNull(response).getIsSuccess()
                && response.getHttpStatus() == HttpStatus.OK) {
            //TODO: how to pass this model attribute to the page???? Maybe add to singletone?
            LOGGER.error("Cannot turn on device with id: " + deviceRoomId);
            model.addAttribute("error", "can.not.on.device");
        } else if (response.getHttpStatus() == HttpStatus.NOT_FOUND) {
            LOGGER.error("No parameter to on the device with id: " + deviceRoomId);
            model.addAttribute("error", "no.parameter.to.on.device");
        } else{
            LOGGER.info("The device with id: " + deviceRoomId + " is on" );
        }
        return "redirect:/resident/my-devices/" + room.getId();
    }

    /**
     * Turns off the device.
     *
     * @param deviceRoomId the device room id
     * @param room         the room
     * @return the redirection to the page with device list
     */
    @GetMapping("/off-device/{device-room-id}")
    public String offDevice(@PathVariable("device-room-id") Long deviceRoomId,
                            @ModelAttribute("room") Room room) {
        final String uri = "http://localhost:8081/sensor/off-device/";
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getForObject(
                uri + deviceRoomId, Boolean.class);
        LOGGER.info("The device with id: " + deviceRoomId + " is off" );
        //TODO: add exception catching
        return "redirect:/resident/my-devices/" + room.getId();
    }

    /**
     * Gets the page with the form for setting up device parameters.
     *
     * @param deviceRoomId the device room id
     * @param room         the room
     * @param model        the model
     * @return the page with the form for setting up room parameters
     * or the page with list of devices if device doesn't exist.
     */
    @GetMapping("/set-up-parameter/{device-room-id}")
    public String getSetParameterForm(@PathVariable("device-room-id")
                                              Long deviceRoomId,
                                      @ModelAttribute("room") Room room,
                                      Model model) {
        try {
            DeviceRoom deviceRoom =
                    deviceService.getDeviceRoomById(deviceRoomId);
            model.addAttribute("deviceRoom", deviceRoom);
            List<DeviceParameter> deviceParameters =
                    deviceParameterService.getDeviceParametersByDeviceRoom(deviceRoom);
            model.addAttribute("parameters", deviceParameters);
            model.addAttribute("setUpParameterDto", new SetUpParameterDto());
            return "/client/set-parameter";
        } catch (IllegalArgumentException e) {
            LOGGER.error(rb.getString(e.getMessage()));
            model.addAttribute("error", e.getMessage());
            return "redirect:/resident/my-devices/" + room.getId();
        }
    }

    /**
     * Sets up device parameter values.
     *
     * @param setUpParameterDto the set up parameter dto
     * @param room              the room
     * @param model             the model
     * @return the redirection to the page with list of devices
     */
    @PostMapping("/set-up-parameter")
    public String setUpParameterValue(@ModelAttribute("setUpParameterDto")
                                              SetUpParameterDto setUpParameterDto,
                                      @ModelAttribute("room") Room room, @CurrentUser User user,
                                      Model model) {
        final String uri = "http://localhost:8081/sensor/set-up-parameter-value";
        RestTemplate restTemplate = new RestTemplate();
        DeviceParameter dp = restTemplate.postForObject(
                uri, setUpParameterDto, DeviceParameter.class);
        if (dp != null) {
            return "redirect:/resident/my-devices/" + room.getId();
        } else {
            LOGGER.error(user.getUsername() + " There are no parameter with" +
                    " such id: " + setUpParameterDto.getParameterId());
            model.addAttribute("no.parameter.with.such.id");
            return "/client/set-parameter";
        }
    }

    /**
     * Gets the page with the form for adding room parameters.
     *
     * @param roomId the room id
     * @param model  the model
     * @return the page with the form for adding room parameters.
     */
    @GetMapping("/set-up-room-parameters/{room-id}")
    public String getRoomParametersForm(@PathVariable("room-id") Long roomId,
                                        Model model) {
        try {
            Room room = roomService.getById(roomId);
            model.addAttribute("room", room);
            model.addAttribute("roomParametersDto", new RoomParameterDto());
            model.addAttribute("error", null);
            return "/client/set-up-room-parameters";
        } catch (IllegalArgumentException e) {
            LOGGER.error(rb.getString(e.getMessage()));
            model.addAttribute("error", e.getMessage());
            return "client/set-up-room-parameters";
        }
    }

    /**
     * Adds room parameters.
     *
     * @param roomParameterDto the room parameter dto
     * @param room             the room
     * @param bindingResult    the binding result
     * @param model            the model
     * @return the redirection to the page with room list
     */
    @PostMapping("set-up-room-parameters")
    public String addRoomParameters(@Valid @ModelAttribute("roomParameterDto")
                                            RoomParameterDto roomParameterDto,
                                    @ModelAttribute("room") Room room,
                                    BindingResult bindingResult, @CurrentUser User user,
                                    Model model) {
        if (bindingResult.hasErrors()) {
            return "redirect:/resident/set-up-room-parameters/" + room.getId();
        }
        final String uri = "http://localhost:8081/sensor/set-up-room-parameters";
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.postForObject(uri, new RoomParametersApi(
                roomParameterDto, room), String.class);
        LOGGER.info(user.getUsername() + "added room parameter " + roomParameterDto.toString());
        return "redirect:/resident/my-rooms";
    }

    /**
     * Simulate a fire. Sets up room parameters like parameters when
     * a fire.
     *
     * @param roomId the room id
     * @return the redirection to the page with room list.
     */
    @GetMapping("simulate-fire/{room-id}")
    public String simulateFire(@PathVariable("room-id") Long roomId, @CurrentUser User user) {
        final String uri = "http://localhost:8081/sensor/simulate-fire/";
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(uri + roomId, String.class);
        LOGGER.info(user.getUsername() + " simulates a fire in the room with id: " + roomId);
        return "redirect:/resident/my-rooms";
    }

    /**
     * Simulates flood. Sets up room parameters like parameters when flood.
     *
     * @param roomId the room id
     * @return the redirection to the page with room list.
     */
    @GetMapping("simulate-flood/{room-id}")
    public String simulateFlood(@PathVariable("room-id") Long roomId, @CurrentUser User user) {
        final String uri = "http://localhost:8081/sensor/simulate-flood/";
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(uri + roomId, String.class);
        LOGGER.info(user.getUsername() + " simulates a fire in the room with id: " + roomId);
        return "redirect:/resident/my-rooms";
    }

    /**
     * Simulate an open window. Sets up room parameters like parameters when
     * open window.
     *
     * @param roomId the room id
     * @return the redirection to the page with room list.
     */
    @GetMapping("simulate-open-window/{room-id}")
    public String simulateOpenWindow(@PathVariable("room-id") Long roomId, @CurrentUser User user) {
        final String uri = "http://localhost:8081/sensor/simulate-open-window/";
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(uri + roomId, String.class);
        LOGGER.info(user.getUsername() + " simulates an open window in the room with id: " + roomId);
        return "redirect:/resident/my-rooms";
    }

    /**
     * Checks the house for emergencies each 10 sec.
     */
    @Scheduled(cron = "10 * * * * * ")
    public void check() {
        final String uri = "http://localhost:8081/sensor/check";
        RestTemplate restTemplate = new RestTemplate();
        if (house != null) {
            MessageList response = restTemplate.postForObject(
                    uri, house, MessageList.class);
            EmergencyData.getInstance(response);
        }
    }
}
