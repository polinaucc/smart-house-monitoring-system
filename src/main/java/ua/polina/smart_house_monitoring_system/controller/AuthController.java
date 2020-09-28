package ua.polina.smart_house_monitoring_system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.polina.smart_house_monitoring_system.dto.SignUpDto;
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
            System.out.println(signUpDto.getBirthday());
            userService.saveNewUser(signUpDto);
            return "redirect:/auth/login";
        } catch (Exception ex) {
            model.addAttribute("error", ex.getMessage());
            return "register-client";
        }
    }
}
