package ua.polina.smart_house_monitoring_system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ua.polina.smart_house_monitoring_system.dto.SignUpDto;
import ua.polina.smart_house_monitoring_system.entity.Resident;
import ua.polina.smart_house_monitoring_system.entity.Role;
import ua.polina.smart_house_monitoring_system.entity.User;
import ua.polina.smart_house_monitoring_system.exception.DataExistsException;
import ua.polina.smart_house_monitoring_system.repository.ResidentRepository;
import ua.polina.smart_house_monitoring_system.repository.UserRepository;
import ua.polina.smart_house_monitoring_system.service.HouseService;
import ua.polina.smart_house_monitoring_system.service.UserService;

import javax.transaction.Transactional;
import java.util.HashSet;

/**
 * The User service implementation.
 */
@Service
public class UserServiceImpl implements UserService {
    /**
     * The User repository.
     */
    private UserRepository userRepository;
    /**
     * The Resident repository.
     */
    private ResidentRepository residentRepository;
    /**
     * The Password encoder.
     */
    private PasswordEncoder passwordEncoder;

    /**
     * The house service.
     */
    private HouseService houseService;

    /**
     * Instantiates a new User service.
     *
     * @param userRepository     the user repository
     * @param residentRepository the resident repository
     * @param passwordEncoder    the password encoder
     * @param houseService       the house service
     */
    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           ResidentRepository residentRepository,
                           PasswordEncoder passwordEncoder,
                           HouseService houseService) {
        this.userRepository = userRepository;
        this.residentRepository = residentRepository;
        this.passwordEncoder = passwordEncoder;
        this.houseService = houseService;
    }

    /**
     * Save new user to the database with information from registration form.
     *
     * @param signUpDto the sign up dto from registration form
     * @return User that is saved to database
     * @throws DataExistsException if user with entered username already exists
     *                             in database
     */
    public User saveNewUser(SignUpDto signUpDto) {
        if (userRepository.existsByUsername(signUpDto.getUsername())) {
            throw new DataExistsException("username.exists");
        }
        HashSet<Role> roles = new HashSet<>();
        if (signUpDto.getRole()) {
            roles.add(Role.OWNER);
        } else {
            roles.add(Role.RESIDENT);
        }

        User user = User.builder()
                .authorities(roles)
                .username(signUpDto.getUsername())
                .password(passwordEncoder.encode(signUpDto.getPassword()))
                .build();

        return userRepository.save(user);
    }

    /**
     * Adds to database information about the resident if the user is
     * successfully saved.
     *
     * @param signUpDto the sign up dto
     * @return Resident that is saved to database
     * @throws DataExistsException if resident with entered passport already
     *                             exists
     *                             in database.
     */
    @Override
    @Transactional
    public Resident saveNewResident(SignUpDto signUpDto) {
        User user = saveNewUser(signUpDto);

        if (residentRepository.existsResidentByPassport(
                signUpDto.getPassport())) {
            throw new DataExistsException("passport.already.exists");
        }

        Resident resident = Resident.builder()
                .firstName(signUpDto.getFirstName())
                .firstNameRu(signUpDto.getFirstNameRu())
                .middleName(signUpDto.getMiddleName())
                .middleNameRu(signUpDto.getMiddleNameRu())
                .lastName(signUpDto.getLastName())
                .lastNameRu(signUpDto.getLastNameRu())
                .passport(signUpDto.getPassport())
                .birthday(signUpDto.getBirthday())
                .house(houseService.getHouseByAddressId(signUpDto.getAddressId()))
                .user(user)
                .build();

        return residentRepository.save(resident);
    }

    /**
     * Gets a resident by user.
     *
     * @param user the user
     * @return the resident if he exists, otherwise exception
     * @throws IllegalArgumentException if the resident by such user
     *                                  doesn't exists
     */
    @Override
    public Resident getResidentByUser(User user) {
        return residentRepository.findByUser(user)
                .orElseThrow(() -> new IllegalArgumentException("no.such.resident"));
    }
}
