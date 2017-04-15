package ua.epam.spring.hometask.mvc.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class UserDetailsImpl implements UserDetails {

    String username;

    public UserDetailsImpl(String username) {
        this.username = username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        ArrayList<GrantedAuthority> result = new ArrayList<>();
        if ("1".equals(username) || "2".equals(username)) {
            result.add(new GrantedAuthorityImpl("ROLE_REGISTERED_USER"));
        }
        if ("2".equals(username)) {
            result.add(new GrantedAuthorityImpl("ROLE_BOOKING_MANAGER"));
        }
        return result;
    }

    @Override
    public String getPassword() {
        if ("1".equals(username)) return "1";
        if ("2".equals(username)) return "2";
        if ("3".equals(username)) return "3";
        return null;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
