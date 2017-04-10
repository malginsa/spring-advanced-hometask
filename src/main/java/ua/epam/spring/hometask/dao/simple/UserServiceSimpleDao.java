package ua.epam.spring.hometask.dao.simple;

import ua.epam.spring.hometask.dao.UserServiceDao;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.Ticket;
import ua.epam.spring.hometask.domain.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class UserServiceSimpleDao implements UserServiceDao{

    private static final AtomicLong counter = new AtomicLong(0);

    Collection<User> storage;

    public UserServiceSimpleDao() {
        storage = new ArrayList<>();
    }

    @Override
    public User getUserByEmail(String email) {
        return storage.stream()
                .filter(u -> u.getEmail().equals(email))
                .findFirst()
                .orElse(null);
    }

    @Override
    public User save(User user) {
        if (storage.contains(user)) {
            return user;
        }
        user.setId(counter.incrementAndGet());
        storage.add(user);
        return user;
    }

    @Override
    public void remove(User user) {
        storage.remove(user);
    }

    @Override
    public User getById(Long id) {
        return storage.stream()
                .filter(u-> (u.getId() == id))
                .findFirst().
                orElse(null);
    }

    @Override
    public Collection<User> getAllUsers() {
        return new ArrayList<User>(storage);
    }

    @Override
    public Long getUsersCount() {
        Number size = storage.size();
        return size.longValue();
    }

    @Override
    public Set<Ticket> getPurchasedTicketsForEvent(Event event,
                                                   LocalDateTime dateTime) {
        return storage.stream()
                .map(u -> u.getTickets())
                .flatMap(s -> s.stream())
                .filter(t -> (t.getEvent().equals(event)) && (t.getDateTime().equals(dateTime)))
                .collect(Collectors.toSet());
    }

    @Override
    public Collection<User> getUsersByName(String firstName) {
        return storage.stream()
                .filter(u -> u.getFirstName().equals(firstName))
                .collect(Collectors.toList());
    }
}
