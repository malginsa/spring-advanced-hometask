package ua.epam.spring.hometask.service;

import java.util.Optional;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import ua.epam.spring.hometask.domain.Auditorium;

/**
 * @author Yuriy_Tkach and Sergei_Malgin
 */
public interface AuditoriumService {

    /**
     * Getting all auditoriums from the system
     * 
     * @return set of all auditoriums
     */
    public @Nonnull Set<Auditorium> getAll();

    /**
     * Finding auditorium by name
     * 
     * @param name
     *            Name of the auditorium
     * @return found auditorium or <code>null</code>
     */
    public Optional<Auditorium> getByName(@Nonnull String name);

    public void add(Auditorium auditorium);
    
}
