package ua.epam.spring.hometask.domain;

import org.hibernate.annotations.Proxy;
import ua.epam.spring.hometask.ws.adapter.LocalDateAdapter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * @author Yuriy_Tkach
 */
@Entity
@Proxy(lazy = false)
//@XmlRootElement
public class User extends DomainObject {

    // TODO add field "login"

    @XmlElement(required = true)
    private String firstName;

    @XmlElement(required = false)
    private String lastName;

    @XmlElement(required = false)
    private String email;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//  TODO          ,orphanRemoval = true)
    @OrderBy("dateTime")
    private SortedSet<Ticket> tickets = new TreeSet<>();

    @XmlElement(required = false)
    private LocalDate bithday;

    @XmlElement(required = false)
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @XmlElement(required = false)
    private Set<UserRole> roles;

    @OneToOne(mappedBy = "user", fetch = FetchType.EAGER,
            cascade = CascadeType.ALL, orphanRemoval = true)
    private UserAccount account;

    public User() {
        roles = new HashSet<UserRole>();
        roles.add(UserRole.ROLE_REGISTERED_USER);
        account = new UserAccount(this);
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

    @XmlAttribute(required = true)
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    public LocalDate getBithday() {
        return bithday;
    }

    public User setBithday(LocalDate bithday) {
        this.bithday = bithday;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public Set<UserRole> getRoles() {
        return roles;
    }

    public User setRoles(Set<UserRole> roles) {
        this.roles = roles;
        return this;
    }

    public UserAccount getAccount() {
        return account;
    }

    public void setAccount(UserAccount account) {
        this.account = account;
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
                ", tickets=" + tickets +
                ", bithday=" + bithday +
                ", roles=" + roles +
                '}';
    }

    public User addRole(UserRole role) {
        roles.add(role);
        return this;
    }
}
