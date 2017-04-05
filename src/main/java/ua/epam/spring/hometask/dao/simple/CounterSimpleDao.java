package ua.epam.spring.hometask.dao.simple;

import ua.epam.spring.hometask.dao.CounterDao;

import java.util.HashMap;
import java.util.Map;

public class CounterSimpleDao implements CounterDao {

    private Map<String, Integer> storage;

    public CounterSimpleDao() {
        storage = new HashMap<>();
    }

    @Override
    public int getCounter(String name) {
        return storage.containsKey(name) ? storage.get(name) : 0;
    }

    @Override
    public void incCounter(String name) {
        Integer count = 1;
        if (storage.containsKey(name)) {
            count = storage.get(name);
            count += 1;
        }
        storage.put(name, count);
    }
}
