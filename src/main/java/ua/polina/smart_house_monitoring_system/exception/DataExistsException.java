package ua.polina.smart_house_monitoring_system.exception;

import org.springframework.dao.DuplicateKeyException;

public class DataExistsException extends DuplicateKeyException {
    public DataExistsException(String msg) {
        super(msg);
    }
}
