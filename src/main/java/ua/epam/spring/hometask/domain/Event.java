package ua.epam.spring.hometask.domain;

import org.hibernate.annotations.Proxy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.NavigableMap;
import java.util.Objects;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.xml.bind.annotation.XmlElement;

/**
 * @author Yuriy_Tkach and Sergei Malgin
 */
@Entity
@Proxy(lazy = false)
//@XmlRootElement
public class Event extends DomainObject {

    @Column(nullable = false)
    @XmlElement(required = true)
    private String name;
//    TODO @NaturalId

    @ElementCollection(fetch = FetchType.EAGER)
    @XmlElement(required = false)
    private Set<LocalDateTime> airDates;
//    TODO private SortedSet<LocalDateTime> airDates;

    /**
     * base price, users can have discount
      */
    @XmlElement(required = false)
    private double ticketPrice;

    @Enumerated(EnumType.STRING)
    @XmlElement(required = false)
    private EventRating rating;

    @ManyToMany(fetch = FetchType.EAGER)
    private Map<LocalDateTime, Auditorium> auditoriums;

    public Event() {
        airDates = new TreeSet<>();
        auditoriums = new TreeMap<>();
    }

    /**
     * Checks if event is aired on particular <code>dateTime</code> and assigns
     * auditorium to it.
     * 
     * @param dateTime
     *            Date and time of aired event for which to assign
     * @param auditorium
     *            Auditorium that should be assigned
     * @return <code>true</code> if successful, <code>false</code> if event is
     *         not aired on that date
     */
    public boolean assignAuditorium(LocalDateTime dateTime, Auditorium auditorium) {
        if (airDates.contains(dateTime)) {
            auditoriums.put(dateTime, auditorium);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Removes auditorium assignment from event
     * 
     * @param dateTime
     *            Date and time to remove auditorium for
     * @return <code>true</code> if successful, <code>false</code> if not
     *         removed
     */
    public boolean removeAuditoriumAssignment(LocalDateTime dateTime) {
        return auditoriums.remove(dateTime) != null;
    }

    /**
     * Add date and time of event air
     * 
     * @param dateTime
     *            Date and time to save
     * @return <code>true</code> if successful, <code>false</code> if already
     *         there
     */
    public boolean addAirDateTime(LocalDateTime dateTime) {
        return airDates.add(dateTime);
    }

    /**
     * Adding date and time of event air and assigning auditorium to that
     * 
     * @param dateTime
     *            Date and time to save
     * @param auditorium
     *            Auditorium to save if success in date time save
     * @return <code>true</code> if successful, <code>false</code> if already
     *         there
     */
    public boolean addAirDateTime(LocalDateTime dateTime, Auditorium auditorium) {
        boolean result = airDates.add(dateTime);
        if (result) {
            auditoriums.put(dateTime, auditorium);
        }
        return result;
    }

    /**
     * Removes the date and time of event air. If auditorium was assigned to
     * that date and time - the assignment is also removed
     * 
     * @param dateTime
     *            Date and time to remove
     * @return <code>true</code> if successful, <code>false</code> if not there
     */
    public boolean removeAirDateTime(LocalDateTime dateTime) {
        boolean result = airDates.remove(dateTime);
        if (result) {
            auditoriums.remove(dateTime);
        }
        return result;
    }

    /**
     * Checks if event airs on particular date and time
     * 
     * @param dateTime
     *            Date and time to check
     * @return <code>true</code> event airs on that date and time
     */
    public boolean airsOnDateTime(LocalDateTime dateTime) {
        return airDates.stream().anyMatch(dt -> dt.equals(dateTime));
    }

    /**
     * Checks if event airs on particular date
     * 
     * @param date
     *            Date to ckeck
     * @return <code>true</code> event airs on that date
     */
    public boolean airsOnDate(LocalDate date) {
        return airDates.stream().anyMatch(dt -> dt.toLocalDate().equals(date));
    }

    /**
     * Checking if event airs on dates between <code>from</code> and
     * <code>to</code> inclusive
     * 
     * @param from
     *            Start date to check
     * @param to
     *            End date to check
     * @return <code>true</code> event airs on dates
     */
    public boolean airsOnDates(LocalDate from, LocalDate to) {
        return airDates.stream()
                .anyMatch(dt -> dt.toLocalDate().compareTo(from) >= 0 && dt.toLocalDate().compareTo(to) <= 0);
    }

    public String getName() {
        return name;
    }

    public Event setName(String name) {
        this.name = name;
        return this;
    }

//    public SortedSet<LocalDateTime> getAirDates() {
//        return airDates;
//    }
    public Set<LocalDateTime> getAirDates() {
        return airDates;
    }

    public Event setAirDates(SortedSet<LocalDateTime> airDates) {
        this.airDates = airDates;
        return this;
    }

    public double getTicketPrice() {
        return ticketPrice;
    }

    public Event setTicketPrice(double ticketPrice) {
        this.ticketPrice = ticketPrice;
        return this;
    }

    public EventRating getRating() {
        return rating;
    }

    public Event setRating(EventRating rating) {
        this.rating = rating;
        return this;
    }

    public Map<LocalDateTime, Auditorium> getAuditoriums() {
        return auditoriums;
    }

    public Event setAuditoriums(NavigableMap<LocalDateTime, Auditorium> auditoriums) {
        this.auditoriums = auditoriums;
        return this;
    }

    public Auditorium getAuditorium(LocalDateTime dateTime) {
        return auditoriums.get(dateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Event other = (Event) obj;
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Event{" +
                "name='" + name + '\'' +
                ", airDates=" + airDates +
                ", ticketPrice=" + ticketPrice +
                ", rating=" + rating +
                ", auditoriums=" + auditoriums +
                '}';
    }
}
