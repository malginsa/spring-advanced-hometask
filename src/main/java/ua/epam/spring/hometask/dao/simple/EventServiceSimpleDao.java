package ua.epam.spring.hometask.dao.simple;

import ua.epam.spring.hometask.dao.EventServiceDao;
import ua.epam.spring.hometask.domain.Event;

import javax.annotation.Nonnull;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class EventServiceSimpleDao implements EventServiceDao {

	private static final AtomicLong counter = new AtomicLong(0);

	private Map<Long, Event> storage;

    public EventServiceSimpleDao() {
        this.storage = new HashMap<>();
    }

	@Override
	public Event save(Event event) {
		if (!storage.values().contains(event)) {
			event.setId(counter.incrementAndGet());
		}
		storage.put(event.getId(), event);
        return event;
	}

	@Override
	public void remove(Event event) {
		storage.remove(event.getId());
	}

	@Override
	public Event getById(Long id) {
		return storage.get(id);
	}

	@Override
	public List<Event> getAll() {
		return new ArrayList(storage.values());
	}

	@Override
	public Optional<Event> getByName(String name) {
		return storage.values().stream()
				.filter(v -> v.getName().equals(name))
				.findFirst();
	}

	@Nonnull
	@Override
	public Set<Event> getForDateRange(@Nonnull LocalDate from,
									  @Nonnull LocalDate to) {
        return storage.values().stream()
                .filter(e -> e.airsOnDates(from, to))
                .collect(Collectors.toSet());
	}
}
