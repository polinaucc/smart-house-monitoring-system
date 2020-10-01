package ua.polina.smart_house_monitoring_system.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
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
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignUpDto {
    @NotBlank
    @Size(min = 5, max = 40, message = "{username.error}")
    private String username;

    @NotBlank
    @Size(min = 5, max = 20, message = "{password.error}")
    private String password;

    @NotBlank
    @Pattern(regexp = "^[A-Z][a-z]{4,19}", message = "{first.name.error}")
    private String firstName;

    @NotBlank
    @Pattern(regexp = "^[А-ЩЮЯЭ][а-я]{3,19}$", message = "{ru.error}")
    private String firstNameRu;

    @NotBlank
    @Pattern(regexp = "^[A-Z][a-z]{4,19}", message = "{middle.name.error}")
    private String middleName;

    @NotBlank
    @Pattern(regexp = "(^[А-ЩЮЯЭ][а-я]{3,19})(ович$|евич$|евна$|овна$)",
            message = "{middle.name.ru.error}")
    private String middleNameRu;

    @NotBlank
    @Pattern(regexp = "^[A-Z][a-z]{4,19}", message = "{last.name.error}")
    private String lastName;

    @NotBlank
    @Pattern(regexp = "^[А-ЩЮЯЭ][а-я]{4,20}$", message = "{ru.error}")
    private String lastNameRu;

    @NotBlank
    @Pattern(regexp = "^[А-Я]{2}[0-9]{6}$", message = "{illegal.passport}")
    private String passport;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Past(message = "{wrong.format}")
    private LocalDate birthday;
    private Boolean role;
}
