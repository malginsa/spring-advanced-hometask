package ua.epam.spring.hometask.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ua.epam.spring.hometask.dao.UserServiceDao;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.service.UserService;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;

@Component("userService")
public class UserServiceImpl implements UserService {

    private UserServiceDao dao;

    @Autowired
    public UserServiceImpl(@Qualifier("userServiceDao") UserServiceDao dao) {
        this.dao = dao;
    }

    @Nullable
    @Override
    public User getUserByEmail(@Nonnull String email) {
        return dao.getUserByEmail(email);
    }

    @Override
    public Collection<User> getUsersByName(@Nonnull String firstName) {
        return dao.getUsersByName(firstName);
    }

    @Override
    public User save(@Nonnull User user) {
        return dao.save(user);
    }

    @Override
    public void remove(@Nonnull User user) {
        dao.remove(user);
    }

    @Override
    public User getById(@Nonnull Long id) {
        return dao.getById(id);
    }

    @Nonnull
    @Override
    public Collection<User> getAll() {
        return dao.getAllUsers();
    }
}
