package ua.epam.spring.hometask.domain;

import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * @author Yuriy_Tkach
 */
@Entity
@Proxy(lazy = false)
public class User extends DomainObject {

    private String firstName;

    private String lastName;

    private String email;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//  TODO          ,orphanRemoval = true)
    @OrderBy("dateTime")
    private SortedSet<Ticket> tickets = new TreeSet<>();

    private LocalDate bithday;

    public User() {
    }

    public String getFirstName() {
        return firstName;
    }

    public User setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public User setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public SortedSet<Ticket> getTickets() {
        return tickets;
    }

    public User setTickets(SortedSet<Ticket> tickets) {
        this.tickets = tickets;
        return this;
    }

    public LocalDate getBithday() {
        return bithday;
    }

    public User setBithday(LocalDate bithday) {
        this.bithday = bithday;
        return this;
    }

    public boolean addTicket(Ticket ticket) {
        return tickets.add(ticket);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, email, bithday);
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
        User other = (User) obj;
        if (email == null) {
            if (other.email != null) {
                return false;
            }
        } else if (!email.equals(other.email)) {
            return false;
        }
        if (firstName == null) {
            if (other.firstName != null) {
                return false;
            }
        } else if (!firstName.equals(other.firstName)) {
            return false;
        }
        if (lastName == null) {
            if (other.lastName != null) {
                return false;
            }
        } else if (!lastName.equals(other.lastName)) {
            return false;
        }
        if (bithday == null) {
            if (other.bithday != null) {
                return false;
            }
        } else if (!bithday.equals(other.bithday)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", bithday=" + bithday +
                '}';
    }
}
