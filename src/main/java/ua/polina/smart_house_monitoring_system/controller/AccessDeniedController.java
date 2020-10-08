package ua.polina.smart_house_monitoring_system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AccessDeniedController {
    @RequestMapping("/wrong-page")
    public String accessDenied(){
        return "wrong-page";
    }
}
