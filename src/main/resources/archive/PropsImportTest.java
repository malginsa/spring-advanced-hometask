package archive;

import ua.epam.spring.hometask.domain.User;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public class PropsImportTest {

    public static void main(String[] args) {

        Map<String, String> props = new HashMap<>();

        props.put("manager.firstName", "manager");
        props.put("manager.password", "4813c5f92abe8855c0e8acbd0383e484b05dc1fa8518566893317f2f5ac07d7f825d88190fb60588");
        props.put("manager.email", "manager@gmail.com");
        props.put("manager.roles", "ROLE_REGISTERED_USER,ROLE_BOOKING_MANAGER");
        props.put("cashier.firstName", "cashier");
        props.put("cashier.password", "62b4803e2a3bb8c62c9c0ce06070eea1a8ace9cba75fe6ce86a2506f5a2a571e7af74ce9934a41d8");
        props.put("cashier.email", "cashier@gmail.com");
        props.put("cashier.roles", "ROLE_REGISTERED_USER,ROLE_BOOKING_MANAGER");
        props.put("admin.firstName", "admin");
        props.put("admin.password", "e77588750b8c5eb18d37295b45c3edf200942f4a26eb372ad0bc16240afc3f506560ae75c51a0135");
        props.put("admin.email", "admin@gmail.com");
        props.put("admin.roles", "ROLE_REGISTERED_USER,ROLE_BOOKING_MANAGER");
        props.put("reporter.firstName", "reporter");
        props.put("reporter.password", "10a00f412136813ab9acc147c780abb1b0dbfab1470f59d1285d5ab5955665c8bacd4d693d91d50b");
        props.put("reporter.email", "reporter@gmail.com");
        props.put("reporter.roles", "ROLE_REGISTERED_USER");

        Set<String> prefixes = props
                .keySet()
                .stream()
                .map(s -> s.split("\\.")[0])
                .collect(Collectors.toSet());

        BiFunction<User, String, User> setFirstName = User::setFirstName;

        Map<String, BiFunction<User, String, User>> suffixToMethod = new HashMap<>();
        suffixToMethod.put("firstName", User::setFirstName);
        suffixToMethod.put("password", User::setPassword);
        suffixToMethod.put("email", User::setEmail);

        String s = "ROLE_REGISTERED_USER,ROLE_BOOKING_MANAGER";

        // TODO
//        Arrays.stream(s.split(","))
//                .map(UserRole::valueOf)
//                .forEach();

//        suffixToMethod.put("roles", (user, s) -> {});

    }

}



