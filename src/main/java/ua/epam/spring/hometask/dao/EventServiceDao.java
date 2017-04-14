package ua.epam.spring.hometask.dao;

import ua.epam.spring.hometask.domain.Event;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import javax.annotation.Nonnull;

public interface EventServiceDao {

    /**
     *  Persist event
     *  @param event - event to persist
     *  @return saved entity event
     * */
    Event save(Event event);

    void remove(Event event);

    Event getById(Long id);

    List<Event> getAll();
    
    Optional<Event> getByName(String name);

    @Nonnull
    Set<Event> getForDateRange(@Nonnull LocalDate from,
                               @Nonnull LocalDate to);
}
