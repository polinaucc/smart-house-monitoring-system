package ua.polina.smart_house_monitoring_system.api;

public class EmergencyData {
    private static EmergencyData instance;
    public String message;

    private EmergencyData(String message) {
        this.message = message;
    }

    public static EmergencyData getInstance(String message) {
        if(instance == null){
            instance = new EmergencyData(message);
        }
        return instance;
    }

    public static EmergencyData getInstance(){
        return instance;
    }
}
