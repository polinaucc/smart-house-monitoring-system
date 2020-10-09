package ua.polina.smart_house_monitoring_system.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * The type Response on api.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseOnApi {
    /**
     * True if the request was successful.
     */
    private Boolean isSuccess;

    /**
     * The http status of operation.
     */
    private HttpStatus httpStatus;
}
