package ua.polina.smart_house_monitoring_system.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ua.polina.smart_house_monitoring_system.dto.SignUpDto;
import ua.polina.smart_house_monitoring_system.entity.Resident;
import ua.polina.smart_house_monitoring_system.entity.Role;
import ua.polina.smart_house_monitoring_system.entity.User;
import ua.polina.smart_house_monitoring_system.repository.ResidentRepository;
import ua.polina.smart_house_monitoring_system.repository.UserRepository;

import javax.transaction.Transactional;
import java.util.HashSet;

/**
 * The User service implementation
 */
@Service
public class UserServiceImpl implements UserService {
    /**
     * The User repository.
     */
    final UserRepository userRepository;
    /**
     * The Resident repository.
     */
    final ResidentRepository residentRepository;
    /**
     * The Password encoder.
     */
    final PasswordEncoder passwordEncoder;

    /**
     * Instantiates a new User service.
     *
     * @param userRepository     the user repository
     * @param residentRepository the resident repository
     * @param passwordEncoder    the password encoder
     */
    public UserServiceImpl(UserRepository userRepository,
                           ResidentRepository residentRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.residentRepository = residentRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Save new user to the database.
     *
     * @param signUpDto the sign up dto from registration form
     * @return the user
     */
    public User saveNewUser(SignUpDto signUpDto) {
        HashSet<Role> roles = new HashSet<>();
        if (signUpDto.getRole()) roles.add(Role.OWNER);
        else roles.add(Role.RESIDENT);

        User user = User.builder()
                .authorities(roles)
                .username(signUpDto.getUsername())
                .password(passwordEncoder.encode(signUpDto.getPassword()))
                .build();

        return userRepository.save(user);
    }

    @Override
    @Transactional
    public Resident saveNewResident(SignUpDto signUpDto) {
        User user = saveNewUser(signUpDto);

        Resident resident = Resident.builder()
                .firstName(signUpDto.getFirstName())
                .firstNameRu(signUpDto.getFirstNameRu())
                .middleName(signUpDto.getMiddleName())
                .middleNameRu(signUpDto.getMiddleNameRu())
                .lastName(signUpDto.getLastName())
                .lastNameRu(signUpDto.getLastNameRu())
                .passport(signUpDto.getPassport())
                .birthday(signUpDto.getBirthday())
                .user(user)
                .build();

        return residentRepository.save(resident);
    }
}
