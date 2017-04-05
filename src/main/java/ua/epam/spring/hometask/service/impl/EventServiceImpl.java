package ua.epam.spring.hometask.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ua.epam.spring.hometask.dao.EventServiceDao;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.service.EventService;

import javax.annotation.Nonnull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;

@Component("eventService")
public class EventServiceImpl implements EventService {
	
	private EventServiceDao dao;

	@Autowired
	public EventServiceImpl(@Qualifier("eventServiceDao")
                                        EventServiceDao dao) {
		this.dao = dao;
	}

	@Override
	public Event save(Event event) {
		return dao.save(event);
	}

	@Override
	public void remove(Event event) {
		dao.remove(event);
	}

	@Override
	public Event getById(Long id) {
		return dao.getById(id);
	}

	@Override
	public Collection<Event> getAll() {
		return dao.getAll();
	}

	@Override
	public Optional<Event> getByName(String name) {
		return dao.getByName(name);
	}

	@Nonnull
	@Override
	public Set<Event> getForDateRange(@Nonnull LocalDate from,
									  @Nonnull LocalDate to) {
		return dao.getForDateRange(from, to);
	}

	@Nonnull
	@Override
	public Set<Event> getNextEvents(@Nonnull LocalDateTime to) {
		return getForDateRange(LocalDate.now(), to.toLocalDate());
	}
}
