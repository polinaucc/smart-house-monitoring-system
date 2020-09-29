package ua.polina.smart_house_monitoring_system.entity;

import org.springframework.security.core.GrantedAuthority;

/**
 * Roles of Users.
 */
public enum Role implements GrantedAuthority {
    /**
     * Admin role.
     */
    ADMIN,
    /**
     * Owner role.
     */
    OWNER,
    /**
     * Resident role.
     */
    RESIDENT;

    @Override
    public String getAuthority() {
        return name();
    }
}
