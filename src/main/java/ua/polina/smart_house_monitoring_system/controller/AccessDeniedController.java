package ua.polina.smart_house_monitoring_system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * The type Access denied controller.
 */
@Controller
public class AccessDeniedController {
    /**
     * Gets the wrong page if something went wrong.
     *
     * @return the string
     */
    @RequestMapping("/wrong-page")
    public String accessDenied() {
        return "wrong-page";
    }
}
