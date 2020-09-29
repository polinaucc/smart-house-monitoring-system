package ua.polina.smart_house_monitoring_system.service;

import ua.polina.smart_house_monitoring_system.dto.SignUpDto;
import ua.polina.smart_house_monitoring_system.entity.Resident;

public interface UserService {
    Resident saveNewResident(SignUpDto signUpDto);
}
