package ua.epam.spring.hometask.mvc.security;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.service.UserService;

import java.util.Iterator;

@Component("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    private static final Logger LOG = LogManager.getLogger();

    @Autowired
    @Qualifier("userService")
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        Iterator<User> iterator = this.userService
                .getUsersByName(username)
                .iterator();
        if (!iterator.hasNext()) {
            throw new UsernameNotFoundException("Bad credentials");
        }
        User user = iterator.next();
        UserDetailsImpl userDetailsImpl = new UserDetailsImpl(user);
        return userDetailsImpl;
    }
}
