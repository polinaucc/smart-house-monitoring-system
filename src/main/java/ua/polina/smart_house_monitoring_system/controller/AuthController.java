package ua.polina.smart_house_monitoring_system.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.polina.smart_house_monitoring_system.dto.SignUpDto;
import ua.polina.smart_house_monitoring_system.entity.Role;
import ua.polina.smart_house_monitoring_system.service.UserService;


@Controller
@RequestMapping("/auth")
public class AuthController {
    final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/sign-up")
    public String getRegisterPage(Model model) {
        model.addAttribute("signUp", new SignUpDto());
        model.addAttribute("error", null);
        return "register-client";
    }

    @PostMapping("/sign-up")
    public String registerUser(@ModelAttribute("signUp") SignUpDto signUpDto,
                               BindingResult bindingResult,
                               Model model) {
        if (bindingResult.hasErrors()) {
            System.out.println(signUpDto);
            System.out.println("ERRRRORR");
            return "register-client";
        }
        try {
            System.out.println(signUpDto.getRole());
            userService.saveNewResident(signUpDto);
            return "redirect:/auth/login";
        } catch (Exception ex) {
            model.addAttribute("error", ex.getMessage());
            return "register-client";
        }
    }

    @RequestMapping("/login")
    public String getLogin(
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout,
            Model model) {
        model.addAttribute("error", error);
        model.addAttribute("logout", logout);
        return "login";
    }

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
