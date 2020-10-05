package ua.polina.smart_house_monitoring_system.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import ua.polina.smart_house_monitoring_system.dto.SignUpDto;
import ua.polina.smart_house_monitoring_system.entity.Resident;
import ua.polina.smart_house_monitoring_system.entity.User;
import ua.polina.smart_house_monitoring_system.repository.ResidentRepository;
import ua.polina.smart_house_monitoring_system.repository.UserRepository;
import ua.polina.smart_house_monitoring_system.service.impl.UserServiceImpl;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {
    @Mock
    UserRepository userRepository;
    @Mock
    ResidentRepository residentRepository;
    @Mock
    PasswordEncoder passwordEncoder;
    @InjectMocks
    UserServiceImpl userService;
    SignUpDto signUpDto;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        signUpDto = SignUpDto.builder()
                .username("user1")
                .password("user1")
                .firstName("Anton")
                .firstNameRu("Антон")
                .middleName("Volodymyrovych")
                .middleNameRu("Владимирович")
                .lastName("Povarov")
                .lastNameRu("Поваров")
                .passport("АБ456894")
                .birthday(LocalDate.of(1999, 4, 18))
                .role(true)
                .build();
    }

    @Test
    public void saveNewResident() {
        userService.saveNewResident(signUpDto);
        verify(userRepository, times(1)).save(any(User.class));
        verify(residentRepository, times(1)).save(any(Resident.class));

    }
}