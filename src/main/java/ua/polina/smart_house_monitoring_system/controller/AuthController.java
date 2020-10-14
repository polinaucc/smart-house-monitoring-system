package ua.polina.smart_house_monitoring_system.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.polina.smart_house_monitoring_system.api.EmergencyData;
import ua.polina.smart_house_monitoring_system.api.MessageList;
import ua.polina.smart_house_monitoring_system.entity.Role;


/**
 * Controller for authentication and authorization. It processes requests
 * for registration and login
 *
 * @author Polina Serhiienko
 */
@Controller
@RequestMapping("/auth")
public class AuthController {
    /**
     * Gets login. Clears messages about emergencies in the house of previous
     * logged in user.
     *
     * @param error  the error parameter
     * @param logout the logout parameter
     * @param model  the model
     * @return the login page
     */
    @RequestMapping("/login")
    public String getLogin(
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout,
            Model model) {
        model.addAttribute("error", error);
        model.addAttribute("logout", logout);
        EmergencyData.getInstance(new MessageList());
        return "login";
    }

    /**
     * Gets success login page.
     *
     * @param model the model
     * @return the success login page
     */
    @RequestMapping("/default-success")
    public String getSuccessPage(Model model) {
        if (SecurityContextHolder.getContext().getAuthentication()
                .getAuthorities().contains(Role.ADMIN)) {
            return "redirect:/admin/index";
        } else if (SecurityContextHolder.getContext().getAuthentication()
                .getAuthorities().contains(Role.OWNER)) {
            return "redirect:/owner/index";
        } else if (SecurityContextHolder.getContext().getAuthentication()
                .getAuthorities().contains(Role.RESIDENT)) {
            return "redirect:/resident/index";
        }
        return "index";
    }
}
