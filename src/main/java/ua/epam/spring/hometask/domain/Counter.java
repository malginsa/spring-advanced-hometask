package ua.epam.spring.hometask.domain;

import javax.annotation.Nonnull;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
public class Counter extends DomainObject {

    @Nonnull
    @Enumerated(EnumType.STRING)
    private CounterType counterType;

    private String name;

    private int value;

    public Counter() {
    }

    public Counter(CounterType counterType) {
        this.counterType = counterType;
    }

    public String getName() {
        return name;
    }

    public Counter setName(String name) {
        this.name = name;
        return this;
    }

    public int getValue() {
        return value;
    }

    public Counter setValue(int value) {
        this.value = value;
        return this;
    }

    public CounterType getCounterType() {
        return counterType;
    }

    public Counter setCounterType(CounterType counterType) {
        this.counterType = counterType;
        return this;
    }
}
