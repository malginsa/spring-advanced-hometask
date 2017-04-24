package ua.epam.spring.hometask.service.impl;

public class InsufficientMoneyException extends Exception {

    public InsufficientMoneyException() {
        this(" :::Not enough money for booking::: ");
    }

    public InsufficientMoneyException(String message) {
        super(message);
    }
}
