package ua.epam.spring.hometask.dao;

import ua.epam.spring.hometask.domain.Auditorium;

import java.util.Optional;
import java.util.Set;

public interface AuditoriumServiceDao {

	Auditorium save(Auditorium auditorium);

	Optional<Auditorium> getByName(String name);

	Set<Auditorium> getAll();

	boolean remove(Auditorium auditorium);
}
