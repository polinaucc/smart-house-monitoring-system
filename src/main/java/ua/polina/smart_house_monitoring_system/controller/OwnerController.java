package ua.polina.smart_house_monitoring_system.controller;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.polina.smart_house_monitoring_system.dto.DeviceParameterDto;
import ua.polina.smart_house_monitoring_system.dto.DeviceUserDto;
import ua.polina.smart_house_monitoring_system.entity.*;
import ua.polina.smart_house_monitoring_system.exception.OrderException;
import ua.polina.smart_house_monitoring_system.service.DeviceParameterService;
import ua.polina.smart_house_monitoring_system.service.DeviceService;

import javax.validation.Valid;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * The Owner controller. It processes requests for owner role.
 */
@Controller
@SessionAttributes(value = {"room", "deviceRoom"})
@RequestMapping("/owner")
public class OwnerController {
    /**
     * The Logger.
     */
    private static final Logger LOGGER = LogManager.getLogger(OwnerController.class);
    /**
     * The device service.
     */
    private final DeviceService deviceService;
    /**
     * The device parameter service.
     */
    private final DeviceParameterService deviceParameterService;
    /**
     * Resource bundle field for localization error messages
     */
    private ResourceBundle rb;

    /**
     * Instantiates a new Owner controller.
     *
     * @param deviceService          the device service
     * @param deviceParameterService the device parameter service
     */
    @Autowired
    public OwnerController(DeviceService deviceService,
                           DeviceParameterService deviceParameterService) {
        this.deviceService = deviceService;
        this.deviceParameterService = deviceParameterService;
        rb = ResourceBundle.getBundle(
                "messages", new Locale("en", "UK"));
    }

    /**
     * Gets a form for device adding.
     *
     * @param model the model
     * @return the add device form
     */
    @GetMapping("/add-device")
    public String getAddDeviceForm(Model model) {
        List<Device> devices = deviceService.getAllDevices();
        model.addAttribute("deviceList", devices);
        model.addAttribute("deviceDto", new DeviceUserDto());
        model.addAttribute("error", null);
        return "client/add-device";
    }

    /**
     * Adds a device to the room.
     *
     * @param deviceUserDto the device user dto
     * @param room          the room
     * @param model         the model
     * @return the page with the list of devices, if all is ok,
     * otherwise - redirection to the page with the form for device adding.
     */
    @PostMapping("/add-device")
    public String addDevice(@ModelAttribute("deviceDto")
                                    DeviceUserDto deviceUserDto,
                            @ModelAttribute("room") Room room, @CurrentUser User user,
                            Model model) {
        try {
            DeviceRoom deviceInRoom = deviceService.saveDevice(deviceUserDto, room);
            LOGGER.info(user.getUsername() + " added new device: "
                    + deviceInRoom.getDevice().getName() + " to the room: "
                    + deviceInRoom.getRoom().getName());
            return "redirect:/owner/my-devices/" + room.getId();
        } catch (IllegalArgumentException e) {
            LOGGER.error(user.getUsername() + rb.getString(e.getMessage()));
            model.addAttribute("error", e.getMessage());
            return "client/add-device";
        }
    }

    /**
     * Gets the page with the form for parameter adding.
     *
     * @param model the model
     * @return the page with the form for device parameter adding
     */
    @GetMapping("/add-parameter")
    public String getAddParameterForm(Model model) {
        model.addAttribute("deviceParameterDto", new DeviceParameterDto());
        model.addAttribute("error", null);
        return "/client/add-device-parameter";
    }

    /**
     * Adds parameter.
     *
     * @param deviceParameterDto the device parameter dto
     * @param deviceRoom         the device room
     * @param room               the room
     * @param bindingResult      the binding result
     * @param model              the model
     * @return the page with the list of device parameters, if binding
     * result has o errors, otherwise -the page with the form for device
     * parameter adding.
     */
    @PostMapping("/add-parameter")
    public String addParameter(@Valid @ModelAttribute("deviceParameterDto")
                                       DeviceParameterDto deviceParameterDto,
                               @ModelAttribute("deviceRoom") DeviceRoom deviceRoom,
                               @ModelAttribute("room") Room room,
                               BindingResult bindingResult, @CurrentUser User user,
                               Model model) {
        if (bindingResult.hasErrors()) {
            return "/client/add-device-parameter";
        }
        try {
            DeviceParameter deviceParameter = deviceParameterService
                    .saveDeviceParameter(deviceParameterDto, deviceRoom);
            LOGGER.info(user.getUsername() + " added parameter: "
                    + deviceParameter.getName() + " to the device: "
                    + deviceParameter.getRoomDevice().getDevice()
                    + " in the room: "
                    + deviceParameter.getRoomDevice().getRoom());
            return "redirect:/owner/get-parameters/" + deviceRoom.getId();
        } catch (IllegalArgumentException | OrderException e) {
            LOGGER.error(user.getUsername() + rb.getString(e.getMessage()));
            model.addAttribute("error", e.getMessage());
            return "/client/add-device-parameter";
        }
    }


}
