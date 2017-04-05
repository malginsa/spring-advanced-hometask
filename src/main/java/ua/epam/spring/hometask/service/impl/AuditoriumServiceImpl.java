package ua.epam.spring.hometask.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ua.epam.spring.hometask.dao.AuditoriumServiceDao;
import ua.epam.spring.hometask.domain.Auditorium;
import ua.epam.spring.hometask.service.AuditoriumService;

import java.util.Optional;
import java.util.Set;

@Component("auditoriumService")
public class AuditoriumServiceImpl implements AuditoriumService {

    private AuditoriumServiceDao dao;

    @Autowired
    public AuditoriumServiceImpl(@Qualifier("auditoriumServiceDao")
                                             AuditoriumServiceDao dao) {
        this.dao = dao;
    }

    @Override
    public Set<Auditorium> getAll() {
        return dao.getAll();
    }

    @Override
    public Optional<Auditorium> getByName(String name) {
        return dao.getByName(name);
    }

    @Override
    public void add(Auditorium auditorium) {
        Optional<Auditorium> optional = dao.getByName(auditorium.getName());
        if (optional.isPresent()) {
            dao.remove(optional.get());
        }
        dao.save(auditorium);
    }

}
