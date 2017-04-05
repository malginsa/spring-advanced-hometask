package ua.epam.spring.hometask.dao.simple;

import ua.epam.spring.hometask.dao.AuditoriumServiceDao;
import ua.epam.spring.hometask.domain.Auditorium;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

public class AuditoriumServiceSimpleDao implements AuditoriumServiceDao {

    private static final AtomicLong counter = new AtomicLong(0);

            private List<Auditorium> storage;

    public AuditoriumServiceSimpleDao(
            List<Auditorium> storage) {
        this.storage = storage;
        for (Auditorium auditorium : this.storage) {
            auditorium.setId(counter.getAndIncrement());
        }
    }

    @Override
    public Auditorium save(Auditorium auditorium) {
        auditorium.setId(counter.incrementAndGet());
        storage.add(auditorium);
        return auditorium;
    }

    @Override
    public Optional<Auditorium> getByName(String name) {
        return storage.stream()
                .filter(a -> a.getName()
                        .equals(name))
                .findFirst();
    }

    @Override
    public Set<Auditorium> getAll() {
        return new HashSet<>(storage);
    }

    @Override
    public boolean remove(Auditorium auditorium) {
        return storage.remove(auditorium);
    }

}
