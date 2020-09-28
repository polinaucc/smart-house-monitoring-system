package ua.polina.smart_house_monitoring_system.entity;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ADMIN,
    OWNER,
    RESIDENT;

    @Override
    public String getAuthority() {
        return name();
    }
}