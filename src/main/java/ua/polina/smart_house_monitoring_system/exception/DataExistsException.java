package ua.polina.smart_house_monitoring_system.exception;

import org.springframework.dao.DuplicateKeyException;

/**
 * The type Data exists exception. It can be used when the data in db already
 * exists.
 */
public class DataExistsException extends DuplicateKeyException {
    /**
     * Instantiates a new Data exists exception.
     *
     * @param msg the message of exception
     */
    public DataExistsException(String msg) {
        super(msg);
    }
}
