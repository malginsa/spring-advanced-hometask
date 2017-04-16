package ua.epam.spring.hometask.mvc.security;

import org.springframework.security.core.GrantedAuthority;
import ua.epam.spring.hometask.domain.UserRole;

public class GrantedAuthorityImpl implements GrantedAuthority {

    private UserRole userRole;

    public GrantedAuthorityImpl(UserRole userRole) {
        this.userRole = userRole;
    }

    @Override
    public String getAuthority() {
        return userRole.toString();
    }

    @Override
    public String toString() {
        return userRole.toString();
    }
}
