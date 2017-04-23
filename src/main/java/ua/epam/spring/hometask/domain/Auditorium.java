package ua.epam.spring.hometask.domain;

import org.hibernate.annotations.Proxy;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

/**
 * @author Yuriy_Tkach and Sergei_Malgin
 */
@Entity
@Proxy(lazy = false)
public class Auditorium extends DomainObject {

    // TODO naturalID
    private String name;

    private long numberOfSeats;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Long> vipSeats;

    public Auditorium() {
        vipSeats = new HashSet<>();
    }

    public Auditorium(String name, long numberOfSeats, Set<Long> vipSeats) {
        this.name = name;
        this.numberOfSeats = numberOfSeats;
        this.vipSeats = vipSeats;
    }

    /**
     * Counts how many vip seats are there in supplied <code>seats</code>
     *
     * @param seats Seats to process
     * @return number of vip seats in request
     */
    public long countVipSeats(Collection<Long> seats) {
        return seats.stream().filter(seat -> vipSeats.contains(seat)).count();
    }

    public String getName() {
        return name;
    }

    public Auditorium setName(String name) {
        this.name = name;
        return this;
    }

    public long getNumberOfSeats() {
        return numberOfSeats;
    }

    public Auditorium setNumberOfSeats(long numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
        return this;
    }

    public Set<Long> getAllSeats() {
        return LongStream.range(1, numberOfSeats + 1).boxed().collect(Collectors.toSet());
    }

    public Set<Long> getVipSeats() {
        return vipSeats;
    }

    public Auditorium setVipSeats(Set<Long> vipSeats) {
        this.vipSeats = vipSeats;
        return this;
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
        Auditorium other = (Auditorium) obj;
        if (this.name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!this.name.equals(other.name)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Auditorium{" +
                "id=" + getId() + ", " +
                "name='" + name + '\'' +
                ", numberOfSeats=" + numberOfSeats +
                ", vipSeats=" + vipSeats +
                '}';
    }
}
