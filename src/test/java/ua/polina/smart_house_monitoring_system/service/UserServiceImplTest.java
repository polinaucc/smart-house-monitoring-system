package ua.polina.smart_house_monitoring_system.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import ua.polina.smart_house_monitoring_system.dto.SignUpDto;
import ua.polina.smart_house_monitoring_system.entity.House;
import ua.polina.smart_house_monitoring_system.entity.Resident;
import ua.polina.smart_house_monitoring_system.entity.Role;
import ua.polina.smart_house_monitoring_system.entity.User;
import ua.polina.smart_house_monitoring_system.exception.DataExistsException;
import ua.polina.smart_house_monitoring_system.repository.ResidentRepository;
import ua.polina.smart_house_monitoring_system.repository.UserRepository;
import ua.polina.smart_house_monitoring_system.service.impl.UserServiceImpl;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private ResidentRepository residentRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private HouseService houseService;
    @InjectMocks
    private UserServiceImpl userService;
    private SignUpDto signUpDto;
    private Resident expectedResident;
    private User expectedUser;

    @Before
    public void setUp() throws Exception {
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

        expectedUser = User.builder()
                .username(signUpDto.getUsername())
                .password(passwordEncoder.encode(signUpDto.getPassword()))
                .build();

        expectedResident = Resident.builder()
                .firstName(signUpDto.getFirstName())
                .firstNameRu(signUpDto.getFirstNameRu())
                .middleName(signUpDto.getMiddleName())
                .middleNameRu(signUpDto.getMiddleNameRu())
                .lastName(signUpDto.getLastName())
                .lastNameRu(signUpDto.getLastNameRu())
                .passport(signUpDto.getPassport())
                .birthday(signUpDto.getBirthday())
                .build();
    }

    @Test
    public void saveNewResident() {
        House house = new House();
        when(houseService.getHouseByAddressId(null)).thenReturn(house);

        Set<Role> userRoles = new HashSet<>();
        userRoles.add(Role.OWNER);

        expectedUser.setAuthorities(userRoles);
        expectedResident.setUser(expectedUser);
        expectedResident.setHouse(house);

        when(residentRepository.existsResidentByPassport(eq(signUpDto.getPassport()))).thenReturn(false);
        when(userRepository.existsByUsername(signUpDto.getUsername())).thenReturn(false);
        when(userRepository.save(eq(expectedUser))).thenReturn(expectedUser);

        userService.saveNewResident(signUpDto);

        verify(residentRepository, times(1)).save(eq(expectedResident));
    }

    @Test(expected = DataExistsException.class)
    public void saveNewResidentExistsByUsername(){
        when(userRepository.existsByUsername(eq(signUpDto.getUsername()))).thenReturn(true);
        userService.saveNewResident(signUpDto);
    }

    @Test(expected = DataExistsException.class)
    public void saveNewResidentExistsByPassport(){
        when(residentRepository.existsResidentByPassport(eq(signUpDto.getPassport()))).thenReturn(true);
        userService.saveNewResident(signUpDto);
    }

    @Test
    public void saveNewResidentRoleResident(){
        House house = new House();
        when(houseService.getHouseByAddressId(null)).thenReturn(new House());
        signUpDto.setRole(false);

        Set<Role> userRoles = new HashSet<>();
        userRoles.add(Role.RESIDENT);
        expectedUser.setAuthorities(userRoles);
        expectedResident.setUser(expectedUser);
        expectedResident.setHouse(house);

        when(residentRepository.existsResidentByPassport(eq(signUpDto.getPassport()))).thenReturn(false);
        when(userRepository.existsByUsername(signUpDto.getUsername())).thenReturn(false);
        when(userRepository.save(eq(expectedUser))).thenReturn(expectedUser);

        userService.saveNewResident(signUpDto);

        verify(residentRepository, times(1)).save(eq(expectedResident));
    }

    @Test
    public void getResidentByUser(){
        when(residentRepository.findByUser(eq(expectedUser))).thenReturn(Optional.of(expectedResident));
        Resident actualResident = userService.getResidentByUser(expectedUser);
        Assert.assertEquals(expectedResident, actualResident);
    }
}