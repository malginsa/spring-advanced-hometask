package archive;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import ua.epam.spring.hometask.mvc.security.GrantedAuthorityImpl;

import java.util.ArrayList;
import java.util.List;

//@Component("daoAuthenticationProvider")
public class DaoAuthenticationProvider implements AuthenticationProvider {

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String name = authentication.getName();
        String password = (String) authentication.getCredentials();

        List<GrantedAuthority> AUTHORITIES = new ArrayList<GrantedAuthority>(1) {{
            add(new GrantedAuthorityImpl("ROLE_REGISTERED_USER"));
        }};

        System.out.println("name: " + name + "  password: " + password);
        if ("3".equals(name) && "3".equals(password)) {
            User principal = new User(name, password, AUTHORITIES);
            return new UsernamePasswordAuthenticationToken(principal, password, AUTHORITIES);
        }
        throw new AuthenticationException("Incorrect username or password") {{
        }};
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
