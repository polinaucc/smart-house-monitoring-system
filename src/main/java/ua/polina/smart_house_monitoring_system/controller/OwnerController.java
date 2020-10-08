package ua.polina.smart_house_monitoring_system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.polina.smart_house_monitoring_system.dto.DeviceParameterDto;
import ua.polina.smart_house_monitoring_system.dto.DeviceUserDto;
import ua.polina.smart_house_monitoring_system.entity.Device;
import ua.polina.smart_house_monitoring_system.entity.DeviceRoom;
import ua.polina.smart_house_monitoring_system.entity.Room;
import ua.polina.smart_house_monitoring_system.service.DeviceParameterService;
import ua.polina.smart_house_monitoring_system.service.DeviceService;

import javax.validation.Valid;
import java.util.List;

/**
 * The Owner controller. It processes requests for owner role.
 */
@Controller
@SessionAttributes(value = {"room", "deviceRoom"})
@RequestMapping("/owner")
public class OwnerController {
    /**
     * The device service.
     */
    private final DeviceService deviceService;

    /**
     * The device parameter service.
     */
    private final DeviceParameterService deviceParameterService;

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
                            @ModelAttribute("room") Room room, Model model) {
        try {
            System.out.println(room.getId());
            deviceService.saveDevice(deviceUserDto, room);
            return "redirect:/owner/my-devices/" + room.getId();
        } catch (IllegalArgumentException e) {
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
                               BindingResult bindingResult,
                               Model model) {
        if (bindingResult.hasErrors()) {
            return "/client/add-device-parameter";
        }
        try {
            deviceParameterService
                    .saveDeviceParameter(deviceParameterDto, deviceRoom);
            return "redirect:/owner/get-parameters/" + deviceRoom.getId();
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "/client/add-device-parameter";
        }
    }


}
