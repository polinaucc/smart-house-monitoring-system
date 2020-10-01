package ua.polina.smart_house_monitoring_system.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class RoomDto {
    @NotNull
    @Pattern(regexp = "^[А-Я][а-я]+")
    String name;

    @NotNull
    @Min(1)
    Double size;
}
