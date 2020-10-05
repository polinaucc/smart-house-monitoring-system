package ua.polina.smart_house_monitoring_system.controller;

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

/**
 * Controller which processes admin requests.
 */
@Controller
@SessionAttributes("house")
@RequestMapping("/admin/")
public class AdminController {
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
     * Instantiates a new Auth controller.
     *
     * @param userService  the user service
     * @param houseService the house service
     * @param roomService  the room service
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
            return "redirect:/admin/index";
        } catch (DataExistsException ex) {
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
            return "redirect:/admin/rooms/" + house.getId();
        } catch (DataExistsException ex) {
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
            return "rooms";
        } catch (IllegalArgumentException ex) {
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
    public String addRoom(@Valid @ModelAttribute("roomDto") RoomDto roomDto,
                          @ModelAttribute("house") House house,
                          BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            //TODO: add to the form message of NumberFormat annotation (typeMismatch)
            return "/admin/add-room";
        }
        try {
            roomService.saveRoom(roomDto, house);
            return "redirect:/admin/rooms/" + house.getId();
        } catch (IllegalArgumentException e) {
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

    @GetMapping("/delete-room/{room-id}")
    public String deleteRoom(@PathVariable("room-id") Long roomId,
                             @ModelAttribute("house") House house,
                             Model model) {
        try {
            roomService.deleteById(roomId);
            return "redirect:/admin/rooms/" + house.getId();
        } catch (IllegalArgumentException ex) {
            model.addAttribute("error", ex.getMessage());
            return "redirect:/admin/rooms/" + house.getId();
        }

    }

    @GetMapping("/add-device")
    public String getDeviceForm(Model model) {
        model.addAttribute("error", null);
        model.addAttribute("deviceDto", new DeviceDto());
        return "admin/add-device";
    }

    @PostMapping("/add-device")
    public String addDevice(@Valid @ModelAttribute("deviceDto") DeviceDto deviceDto,
                            BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "/admin/add-device";
        }
        try {
            deviceService.saveDevice(deviceDto);
            return "redirect:/admin/index";
        } catch (DataExistsException e) {
            model.addAttribute("error", e.getMessage());
            return "/admin/add-device";
        }
    }

    //TODO: delete house
    //TODO: update rooms
}
