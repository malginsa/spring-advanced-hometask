package ua.epam.spring.hometask.dao;

import ua.epam.spring.hometask.domain.Event;

import javax.annotation.Nonnull;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface EventServiceDao {

    Event save(Event event);

    void remove(Event event);

    Event getById(Long id);

    List<Event> getAll();
    
    Optional<Event> getByName(String name);

    @Nonnull
    Set<Event> getForDateRange(@Nonnull LocalDate from,
                               @Nonnull LocalDate to);
}
