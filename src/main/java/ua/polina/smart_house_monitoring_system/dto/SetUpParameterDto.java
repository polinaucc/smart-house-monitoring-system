package ua.polina.smart_house_monitoring_system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Dto for setting up device parameter by admin.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SetUpParameterDto {
    private Long parameterId;
    private Double value;
}
