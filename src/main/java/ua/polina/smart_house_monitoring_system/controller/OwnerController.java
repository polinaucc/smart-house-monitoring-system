package ua.polina.smart_house_monitoring_system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.polina.smart_house_monitoring_system.dto.DeviceParameterDto;
import ua.polina.smart_house_monitoring_system.dto.DeviceUserDto;
import ua.polina.smart_house_monitoring_system.entity.Device;
import ua.polina.smart_house_monitoring_system.entity.DeviceRoom;
import ua.polina.smart_house_monitoring_system.entity.Room;
import ua.polina.smart_house_monitoring_system.service.DeviceParameterService;
import ua.polina.smart_house_monitoring_system.service.DeviceService;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/owner")
public class OwnerController {
    private final DeviceService deviceService;
    private final DeviceParameterService deviceParameterService;

    @Autowired
    public OwnerController(DeviceService deviceService,
                           DeviceParameterService deviceParameterService) {
        this.deviceService = deviceService;
        this.deviceParameterService = deviceParameterService;
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

    @GetMapping("/add-parameter")
    public String getAddParameterForm(Model model) {
        model.addAttribute("deviceParameterDto", new DeviceParameterDto());
        model.addAttribute("error", null);
        return "/client/add-device-parameter";
    }

    @PostMapping("/add-parameter")
    public String addParameter(@Valid @ModelAttribute("deviceParameterDto") DeviceParameterDto deviceParameterDto,
                               @ModelAttribute("deviceRoom") DeviceRoom deviceRoom,
                               @ModelAttribute("room") Room room, BindingResult bindingResult,
                               Model model) {
        if (bindingResult.hasErrors()) {
            return "/client/add-device-parameter";
        }
        try {
            deviceParameterService.saveDeviceParameter(deviceParameterDto, deviceRoom);
            return "redirect:/owner/get-parameters/" + deviceRoom.getId();
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "/client/add-device-parameter";
        }
    }


}
