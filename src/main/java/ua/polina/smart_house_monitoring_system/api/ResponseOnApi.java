package ua.polina.smart_house_monitoring_system.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseOnApi {
    private Boolean isSuccess;
    private HttpStatus httpStatus;
}
