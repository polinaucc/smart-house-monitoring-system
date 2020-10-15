package ua.polina.smart_house_monitoring_system.controller;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * The type Access denied controller.
 */
@Controller
public class AccessDeniedController {
    private static final Logger LOGGER = LogManager.getLogger(AccessDeniedController.class);

    /**
     * Gets the wrong page if something went wrong.
     *
     * @return the string
     */
    @RequestMapping("/wrong-page")
    public String accessDenied() {
        LOGGER.info("There are no access to this page for " +
                SecurityContextHolder.getContext().getAuthentication().getName());
        return "wrong-page";
    }
}
