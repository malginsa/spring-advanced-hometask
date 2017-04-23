package ua.epam.spring.hometask.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;

@Entity
public class UserAccount extends DomainObject {

    @OneToOne(fetch = FetchType.LAZY)
    private User user;

    private float amount;

    public UserAccount() {
    }

    public UserAccount(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }
}
