package ua.polina.smart_house_monitoring_system.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * The type Message list.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageList {
    /**
     * The Messages.
     */
    List<String> messages;
}
