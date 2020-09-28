package ua.polina.smart_house_monitoring_system.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import java.time.LocalDate;

@Data
public class SignUpDto {
    private String username;
    private String password;
    private String firstName;
    private String firstNameRu;
    private String middleName;
    private String middleNameRu;
    private String lastName;
    private String lastNameRu;
    private String passport;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;
}
