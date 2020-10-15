package ua.polina.smart_house_monitoring_system.controller;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.polina.smart_house_monitoring_system.dto.DeviceDto;
import ua.polina.smart_house_monitoring_system.dto.HouseDto;
import ua.polina.smart_house_monitoring_system.dto.RoomDto;
import ua.polina.smart_house_monitoring_system.dto.SignUpDto;
import ua.polina.smart_house_monitoring_system.entity.Address;
import ua.polina.smart_house_monitoring_system.entity.House;
import ua.polina.smart_house_monitoring_system.entity.Room;
import ua.polina.smart_house_monitoring_system.exception.DataExistsException;
import ua.polina.smart_house_monitoring_system.service.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Controller which processes admin requests.
 */
@Controller
@SessionAttributes("house")
@RequestMapping("/admin")
public class AdminController {
    /**
     * Logger field.
     */
    private static final Logger LOGGER = LogManager.getLogger(
            AdminController.class);
    /**
     * The User service. It processes requests about users
     * from controller to database and vice versa.
     */
    private final UserService userService;
    /**
     * The house service. It processes requests about houses
     * from controller to database and vice versa.
     */
    private final HouseService houseService;
    /**
     * The room service. It processes requests about rooms
     * from controller to database and vice versa.
     */
    private final RoomService roomService;
    /**
     * The address service. It processes requests about adresses
     * from controller to database and vice versa.
     */
    private final AddressService addressService;
    /**
     * The device service.
     */
    private final DeviceService deviceService;
    /**
     * Resource bundle field for localization error messages
     */
    private ResourceBundle rb;

    /**
     * Instantiates a new Auth controller.
     *
     * @param userService   the user service
     * @param houseService  the house service
     * @param roomService   the room service
     * @param deviceService the device service
     */
    public AdminController(UserService userService,
                           HouseService houseService,
                           RoomService roomService,
                           AddressService addressService,
                           DeviceService deviceService) {
        this.userService = userService;
        this.houseService = houseService;
        this.roomService = roomService;
        this.addressService = addressService;
        this.deviceService = deviceService;
        rb = ResourceBundle.getBundle(
                "messages", new Locale("en", "UK"));
    }

    /**
     * Gets registration page.
     *
     * @param model the model
     * @return the registration page
     */
    @GetMapping("/sign-up")
    public String getRegisterPage(Model model) {
        List<Address> addresses = addressService.getAllAddresses();
        model.addAttribute("addresses", addresses);
        model.addAttribute("signUp", new SignUpDto());
        model.addAttribute("error", null);
        return "register-client";
    }

    /**
     * Adds resident to database.
     *
     * @param signUpDto     the sign up dto from form
     * @param bindingResult the binding result
     * @param model         the model
     * @return the page for registration or redirection to login page
     */
    @PostMapping("/sign-up")
    public String registerUser(@Valid @ModelAttribute("signUp")
                                       SignUpDto signUpDto,
                               BindingResult bindingResult,
                               Model model) {
        if (bindingResult.hasErrors()) {
            return "register-client";
        }
        try {
            userService.saveNewResident(signUpDto);
            LOGGER.info("admin registered user: " + signUpDto.getUsername());
            return "redirect:/admin/index";
        } catch (DataExistsException ex) {
            LOGGER.error("admin " + rb.getString(ex.getMessage()));
            model.addAttribute("error", ex.getMessage());
            return "register-client";
        }
    }

    /**
     * Gets the page with the form for house adding.
     *
     * @param model the model
     * @return the page with the form for house adding
     */
    @GetMapping("/add-house")
    public String getHousePage(Model model) {
        model.addAttribute("houseDto", new HouseDto());
        model.addAttribute("error", null);
        return "admin/add-house";
    }

    /**
     * Adds house to database.
     *
     * @param houseDto      the house dto with information from the form
     * @param bindingResult the binding result
     * @param model         the model
     * @return the page for house adding, if the binding result has errors
     * otherwise redirection to the page for room adding
     */
    @PostMapping("/add-house")
    public String addHouse(@Valid @ModelAttribute("houseDto") HouseDto houseDto,
                           BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "admin/add-house";
        }
        try {
            House house = houseService.addNewHouse(houseDto);
            LOGGER.info("admin added a new house");
            return "redirect:/admin/rooms/" + house.getId();
        } catch (DataExistsException ex) {
            LOGGER.error("admin" + rb.getString(ex.getMessage()));
            model.addAttribute("error", ex.getMessage());
            return "admin/add-house";
        }
    }

    /**
     * Gets all house in the system.
     *
     * @param model the model
     * @return the page
     */
    @GetMapping("/houses")
    public String getHouses(Model model) {
        List<House> houses = houseService.getAllHouses();
        model.addAttribute("houses", houses);
        LOGGER.info("admin views all houses in the system");
        return "admin/houses";
    }

    /**
     * Gets the room list of the certain house,
     * calculates the house size.
     *
     * @param houseId unique id of the house
     * @param model   the model
     * @return the page with room list
     */
    @GetMapping("rooms/{id}")
    public String getRooms(@PathVariable("id") Long houseId, Model model) {
        try {
            House house = houseService.getById(houseId);
            List<Room> roomsInHouse = roomService.getRoomsByHouse(house);
            model.addAttribute("rooms", roomsInHouse);
            model.addAttribute("house", house);
            if (roomsInHouse.size() == 0) {
                model.addAttribute("error", "empty.list");
            }
            houseService.updateSize(house, roomsInHouse);
            LOGGER.info("admin views all rooms in the house: " + house);
            return "rooms";
        } catch (IllegalArgumentException ex) {
            LOGGER.error("admin " + rb.getString(ex.getMessage()));
            model.addAttribute("error", ex.getMessage());
            return "rooms";
        }
    }

    /**
     * Gets the page with the form for adding a room to the database.
     *
     * @param model the model
     * @return the page
     */
    @GetMapping("/add-room")
    public String getRoomForm(Model model) {
        model.addAttribute("roomDto", new RoomDto());
        model.addAttribute("error", null);
        return "admin/add-room";
    }

    /**
     * Saves a certain house room with form data to the database,
     * if the data is valid.
     *
     * @param roomDto       the room dto from the form
     * @param house         the house, the room should be saved to
     * @param bindingResult the binding result
     * @param model         the model
     * @return redirection to the page with updated room list with the data
     * if the data is valid and if the amount of house rooms can be increased,
     * otherwise the page with the form for room adding
     */
    @PostMapping("/add-room")
    public String addRoom(@ModelAttribute("roomDto") @Valid RoomDto roomDto,
                          BindingResult bindingResult,
                          @ModelAttribute("house") House house,
                          Model model) {
        if (bindingResult.hasErrors()) {
            return "/admin/add-room";
        }
        try {
            roomService.saveRoom(roomDto, house);
            LOGGER.info("admin saved new room to the house: " + house);
            return "redirect:/admin/rooms/" + house.getId();
        } catch (IllegalArgumentException e) {
            LOGGER.info("admin " + rb.getString(e.getMessage()));
            model.addAttribute("error", e.getMessage());
            return "/admin/add-room";
        }
    }

    /**
     * Gets index page with functions for admin.
     *
     * @return the page
     */
    @GetMapping("/index")
    public String getIndexPage() {
        return "index";
    }

    /**
     * Deletes the room from the house.
     *
     * @param roomId id of the room
     * @param house  id of the house
     * @param model  the model
     * @return the redirection to the page with list of house rooms.
     */
    @GetMapping("/delete-room/{room-id}")
    public String deleteRoom(@PathVariable("room-id") Long roomId,
                             @ModelAttribute("house") House house,
                             Model model) {
        try {
            roomService.deleteById(roomId);
            LOGGER.info("admin deleted the room with id: " + roomId);
            return "redirect:/admin/rooms/" + house.getId();
        } catch (IllegalArgumentException ex) {
            model.addAttribute("error", ex.getMessage());
            return "redirect:/admin/rooms/" + house.getId();
        }

    }

    /**
     * Gets the form for device adding.
     *
     * @param model the model
     * @return the page with form for device adding
     */
    @GetMapping("/add-device")
    public String getDeviceForm(Model model) {
        model.addAttribute("error", null);
        model.addAttribute("deviceDto", new DeviceDto());
        return "admin/add-device";
    }

    /**
     * Adds the device to the system.
     *
     * @param deviceDto     the device dto that contains information about
     *                      device we need to save
     * @param bindingResult the binding result
     * @param model         the model
     * @return if all is ok the redirection to the index page,
     * otherwise - the page for device adding.
     */
    @PostMapping("/add-device")
    public String addDevice(@Valid @ModelAttribute("deviceDto") DeviceDto deviceDto,
                            BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "/admin/add-device";
        }
        try {
            deviceService.saveDevice(deviceDto);
            LOGGER.info("admin added new device: " + deviceDto.getName());
            return "redirect:/admin/index";
        } catch (DataExistsException e) {
            LOGGER.error("admin " + rb.getString(Objects.requireNonNull(
                    e.getMessage())) + deviceDto.getName());
            model.addAttribute("error", e.getMessage());
            return "/admin/add-device";
        }
    }
}
