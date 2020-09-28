package ua.polina.smart_house_monitoring_system.service;

import ua.polina.smart_house_monitoring_system.dto.SignUpDto;
import ua.polina.smart_house_monitoring_system.entity.User;

public interface UserService {
    User saveNewUser(SignUpDto signUpDto);
}
