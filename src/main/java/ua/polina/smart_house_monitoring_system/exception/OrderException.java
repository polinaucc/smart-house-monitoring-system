package ua.polina.smart_house_monitoring_system.exception;


/**
 * The type Order exception. It can be used when user try to set up min value
 * that is more than max one and vice versa.
 */
public class OrderException extends Exception {
    /**
     * Instantiates a new Order exception.
     *
     * @param message the message
     */
    public OrderException(String message) {
        super(message);
    }
}
