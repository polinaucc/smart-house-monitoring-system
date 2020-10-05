package ua.polina.smart_house_monitoring_system.service;

import ua.polina.smart_house_monitoring_system.dto.SignUpDto;
import ua.polina.smart_house_monitoring_system.entity.Resident;
import ua.polina.smart_house_monitoring_system.entity.User;

/**
 * The User service.
 */
public interface UserService {
    /**
     * Save new resident and user to the database.
     *
     * @param signUpDto the sign up dto
     * @return the resident
     */
    Resident saveNewResident(SignUpDto signUpDto);

    public Resident getResidentByUser(User user);
}
