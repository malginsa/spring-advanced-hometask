package ua.epam.spring.hometask.util;

import ua.epam.spring.hometask.domain.UserRole;

import java.util.Arrays;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class EnumTest {

    public static void main(String[] args) {

        String[] split = "REGISTERED_USER,BOOKING_MANAGER".split(",");

        TreeSet<UserRole> collect = Arrays.stream(split)
                .map(UserRole::valueOf)
                .collect(Collectors.toCollection(TreeSet::new));

    }
}
