package ua.epam.spring.hometask.mvc;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public class DaoAuthenticationProvider implements AuthenticationProvider {

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String password = (String) authentication.getCredentials();
        String name = authentication.getName();
        if ("1".equals(name) && "1".equals(password)) {
            authentication.setAuthenticated(true);
        }
        return authentication;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return false;
    }
}
