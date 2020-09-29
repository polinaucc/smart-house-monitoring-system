package ua.polina.smart_house_monitoring_system.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ua.polina.smart_house_monitoring_system.dto.SignUpDto;
import ua.polina.smart_house_monitoring_system.entity.Role;
import ua.polina.smart_house_monitoring_system.entity.User;
import ua.polina.smart_house_monitoring_system.repository.UserRepository;

import javax.transaction.Transactional;
import java.util.HashSet;

@Service
public class UserServiceImpl implements UserService{
    final UserRepository userRepository;

    final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public User saveNewUser(SignUpDto signUpDto) {
        HashSet<Role> roles = new HashSet<>();
        if (signUpDto.getRole()) roles.add(Role.OWNER);
        else roles.add(Role.RESIDENT);

        User user = User.builder()
                .authorities(roles)
                .username(signUpDto.getUsername())
                .password(passwordEncoder.encode(signUpDto.getPassword()))
                .firstName(signUpDto.getFirstName())
                .firstNameRu(signUpDto.getFirstNameRu())
                .middleName(signUpDto.getMiddleName())
                .middleNameRu(signUpDto.getMiddleNameRu())
                .lastName(signUpDto.getLastName())
                .lastNameRu(signUpDto.getLastNameRu())
                .passport(signUpDto.getPassport())
                .birthday(signUpDto.getBirthday())
                .build();

        System.out.println(user);

       return userRepository.save(user);
    }
}
