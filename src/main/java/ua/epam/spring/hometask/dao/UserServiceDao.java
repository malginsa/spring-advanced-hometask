package ua.epam.spring.hometask.dao;

import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.Ticket;
import ua.epam.spring.hometask.domain.User;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;

public interface UserServiceDao {

    User getUserByEmail(String email);

    User save(User user);

    void remove(User user);

    User getById(Long id);

    Collection<User> getAllUsers();

    Long getUsersCount();

    Set<Ticket> getPurchasedTicketsForEvent(Event event, LocalDateTime dateTime);
}
