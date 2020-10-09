package ua.polina.smart_house_monitoring_system.api;

public class EmergencyData {
    private static EmergencyData instance;
    public MessageList messageList;

    private EmergencyData(MessageList messages) {
        this.messageList = messages;
    }

    public static EmergencyData getInstance(MessageList messages) {
        if (instance == null) {
            instance = new EmergencyData(messages);
        }
        instance.messageList = messages;
        return instance;
    }

    public static EmergencyData getInstance() {
        return instance;
    }
}
