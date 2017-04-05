package ua.epam.spring.hometask.domain;

import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author Yuriy_Tkach
 */
@Entity
@Proxy(lazy = false)
public class Ticket extends DomainObject implements Comparable<Ticket> {

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private User user;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Event event;

    private LocalDateTime dateTime;

    private long seat;

    private double price;

    public Ticket() {
    }

    public Ticket(User user, Event event, LocalDateTime dateTime, long seat) {
        this.user = user;
        this.event = event;
        this.dateTime = dateTime;
        this.seat = seat;
    }

    public User getUser() {
        return user;
    }

    public Event getEvent() {
        return event;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public long getSeat() {
        return seat;
    }

    public double getPrice() {
        return price;
    }

    public Ticket setUser(User user) {
        this.user = user;
        return this;
    }

    public Ticket setEvent(Event event) {
        this.event = event;
        return this;
    }

    public Ticket setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
        return this;
    }

    public Ticket setSeat(long seat) {
        this.seat = seat;
        return this;
    }

    public Ticket setPrice(double price) {
        this.price = price;
        return this;
    }

    @Override
    public int hashCode() {
        return Objects.hash(dateTime, event, seat);
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
        Ticket other = (Ticket) obj;
        if (dateTime == null) {
            if (other.dateTime != null) {
                return false;
            }
        } else if (!dateTime.equals(other.dateTime)) {
            return false;
        }
        if (event == null) {
            if (other.event != null) {
                return false;
            }
        } else if (!event.equals(other.event)) {
            return false;
        }
        if (seat != other.seat) {
            return false;
        }
        return true;
    }

    @Override
    public int compareTo(Ticket other) {
        if (this == other) {
            return 0;
        }
        if (other == null) {
            return 1;
        }
        if (getClass() != other.getClass()) {
            return 1;
        }
        int result = dateTime.compareTo(other.getDateTime());

        if (result == 0) {
            result = event.getName().compareTo(other.getEvent().getName());
        }
        if (result == 0) {
            result = Long.compare(seat, other.getSeat());
        }
        return result;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                ", event=" + event +
                ", dateTime=" + dateTime +
                ", seat=" + seat +
                ", price=" + price +
                '}';
    }
}
