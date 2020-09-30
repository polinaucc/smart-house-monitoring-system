package ua.polina.smart_house_monitoring_system.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;

/**
 * The type Sign up dto for registration object.
 */
@Data
public class SignUpDto {
    @NotBlank
    @Size(min = 5, max = 20, message = "Username size error")
    private String username;

    @NotBlank
    @Size(min = 5, max = 20, message = "Password size error")
    private String password;

    @NotBlank
    @Pattern(regexp = "^[A-Z][a-z]{4,19}", message = "First name format error")
    private String firstName;

    @NotBlank
    @Pattern(regexp = "^[А-ЩЮЯЭ][а-я]{3,19}$", message = "Russian first name format error")
    private String firstNameRu;

    @NotBlank
    @Pattern(regexp = "^[A-Z][a-z]{4,19}", message = "Middle name format error")
    private String middleName;

    @NotBlank
    @Pattern(regexp = "(^[А-ЩЮЯЭ][а-я]{5,19})(ович$|евич$|евна$|овна$)",
            message = "Russian middle name format exception")
    private String middleNameRu;

    @NotBlank
    @Pattern(regexp = "^[A-Z][a-z]{4,19}", message = "Last name format exception")
    private String lastName;

    @NotBlank
    @Pattern(regexp = "^[А-ЩЮЯЭ][а-я]{4,20}$", message = "Russian last name format exception")
    private String lastNameRu;

    @NotBlank
    @Pattern(regexp = "^[А-Я]{2}[0-9]{6}$", message = "Passport format exception")
    private String passport;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Past(message = "Date should be in the past")
    private LocalDate birthday;
    private Boolean role;
}
