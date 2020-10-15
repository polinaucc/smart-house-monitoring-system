package ua.polina.smart_house_monitoring_system.api;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * The SingleTone Emergency data. It saves messages about emergencies in
 * the house.
 */
public class EmergencyData {
    /**
     * The Logger for emergencies
     */
    private static final Logger LOGGER = LogManager.getLogger(EmergencyData.class);
    /**
     * EmergencyData instance.
     */
    private static EmergencyData instance;
    /**
     * The list of messages.
     */
    public MessageList messageList;

    private EmergencyData(MessageList messages) {
        this.messageList = messages;
    }

    /**
     * Gets instance. If the instance exists, it just updates it,
     * otherwise creates new instance.
     *
     * @param messages the messages
     * @return the instance
     */
    public static EmergencyData getInstance(MessageList messages) {
        if (instance == null) {
            instance = new EmergencyData(messages);
        }
        instance.messageList = messages;
        if (messages != null && messages.messages != null) {
            messages.messages.forEach(mes ->
                    LOGGER.warn(SecurityContextHolder.getContext()
                            .getAuthentication().getName() + " " + mes));
        }
        return instance;
    }

    /**
     * Gets the current instance.
     *
     * @return the instance
     */
    public static EmergencyData getInstance() {
        return instance;
    }
}
