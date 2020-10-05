package ua.polina.smart_house_monitoring_system.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * The type House dto for house object
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HouseDto {
    @NotBlank
    @Pattern(regexp = "^[А-Я][а-я]{3,14}$",
            message = "{country.city.street.error}")
    private String country;

    @NotBlank
    @Pattern(regexp = "^[А-Я][а-я]{3,14}$",
            message = "{country.city.street.error}")
    private String city;

    @NotBlank
    @Pattern(regexp = "^[А-Я][а-я]{3,14}$",
            message = "{country.city.street.error}")
    private String street;

    @NotBlank
    @Pattern(regexp = "^[0-9]{0,3}[а-я]{0,3}",
            message = "{house.flat.number.error}")
    private String houseNumber;

    @Pattern(regexp = "^[0-9]{0,3}[а-я]{0,3}",
            message = "{house.flat.number.error}")
    private String flatNumber;

    //TODO: delete comment
//    @Min(value = 10, message = "{size.error}")
//    private Double size;

    @Min(1)
    private Integer amountOfRooms;
}
