package ua.polina.smart_house_monitoring_system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.polina.smart_house_monitoring_system.dto.HouseDto;
import ua.polina.smart_house_monitoring_system.dto.SignUpDto;
import ua.polina.smart_house_monitoring_system.exception.DataExistsException;
import ua.polina.smart_house_monitoring_system.service.HouseService;
import ua.polina.smart_house_monitoring_system.service.UserService;

import javax.validation.Valid;

/**
 * Controller which processes admin requests.
 */
@Controller
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
     * Instantiates a new Auth controller.
     *
     * @param userService  the user service
     * @param houseService the house service
     */
    public AdminController(UserService userService,
                           HouseService houseService) {
        this.userService = userService;
        this.houseService = houseService;
    }

    /**
     * Gets registration page.
     *
     * @param model the model
     * @return the registration page
     */
    @GetMapping("/sign-up")
    public String getRegisterPage(Model model) {
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
    public String registerUser(@Valid @ModelAttribute("signUp") SignUpDto signUpDto,
                               BindingResult bindingResult,
                               Model model) {
        if (bindingResult.hasErrors()) {
            return "register-client";
        }
        try {
            userService.saveNewResident(signUpDto);
            return "redirect:/auth/login";
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
            houseService.addNewHouse(houseDto);
            //TODO: redirect to the page with room adding
            return "redirect:/admin/houses";
        } catch (DataExistsException ex) {
            model.addAttribute("error", ex.getMessage());
            return "admin/add-house";
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
}
