package ua.polina.smart_house_monitoring_system.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ua.polina.smart_house_monitoring_system.dto.SignUpDto;
import ua.polina.smart_house_monitoring_system.entity.Resident;
import ua.polina.smart_house_monitoring_system.entity.Role;
import ua.polina.smart_house_monitoring_system.entity.User;
import ua.polina.smart_house_monitoring_system.exception.DataExistsException;
import ua.polina.smart_house_monitoring_system.repository.ResidentRepository;
import ua.polina.smart_house_monitoring_system.repository.UserRepository;

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
    private final UserRepository userRepository;
    /**
     * The Resident repository.
     */
    private final ResidentRepository residentRepository;
    /**
     * The Password encoder.
     */
    private final PasswordEncoder passwordEncoder;

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
     * Save new user to the database with information from registration form.
     *
     * @param signUpDto the sign up dto from registration form
     * @return User that is saved to database
     * @throws DataExistsException if user with entered username already exists
     *                             in database
     */
    public User saveNewUser(final SignUpDto signUpDto) {
        if (userRepository.existsByUsername(signUpDto.getUsername())) {
            throw new DataExistsException("username.exists");
        }
        HashSet<Role> roles = new HashSet<>();
//        if (signUpDto.getRole()) {
//            roles.add(Role.OWNER);
//        } else {
//            roles.add(Role.RESIDENT);
//        }
        roles.add(Role.ADMIN);

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
                .user(user)
                .build();

        return residentRepository.save(resident);
    }
}
