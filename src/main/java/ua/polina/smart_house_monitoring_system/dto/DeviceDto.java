package ua.polina.smart_house_monitoring_system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * The type Device dto for device object
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeviceDto {
    @NotBlank
    String name;

}
