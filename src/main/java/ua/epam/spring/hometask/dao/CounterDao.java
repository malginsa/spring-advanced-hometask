package ua.epam.spring.hometask.dao;

public interface CounterDao {

    int getCounter(String name);

    void incCounter(String name);
}
