package ua.epam.spring.hometask.config;

import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ClassPathResource;
import ua.epam.spring.hometask.domain.Auditorium;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.domain.UserRole;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Configuration
@EnableAspectJAutoProxy
@Import({
//        CounterAspect.class,      // TODO aspect prevent JPA facility
//        DiscountAspect.class,
//        LuckyWinnerAspect.class,
//        SimpleDaoConfig.class,     // uses in-memory structures
        PersistentDaoConfig.class,  // uses persistent DB
        JpaConfig.class
})
public class AppConfig {

    @Bean
    public static PropertyPlaceholderConfigurer propertyPlaceholderConfigurer() {
        PropertyPlaceholderConfigurer ppc = new PropertyPlaceholderConfigurer();
        ppc.setLocations(
                new ClassPathResource("auditorium.properties"),
                new ClassPathResource("discounts.properties"),
                new ClassPathResource("user.properties")
        );
        ppc.setIgnoreUnresolvablePlaceholders(true);
        ppc.setSystemPropertiesMode(PropertyPlaceholderConfigurer.
                SYSTEM_PROPERTIES_MODE_OVERRIDE);
        return ppc;
    }

    @Bean()
    public static PropertiesFactoryBean auditoriumPropsBean() {
        PropertiesFactoryBean bean = new PropertiesFactoryBean();
        bean.setLocation(new ClassPathResource("auditorium.properties"));
        return bean;
    }

    @Resource(name = "auditoriumPropsBean")
    private Map<String, String> auditoriumProps;

//    TODO refactor auditoriums() and users() using lambda
    @Bean()
    public List<Auditorium> predefinedAuditoriums() {

        Set<String> prefixes = auditoriumProps
                .keySet()
                .stream()
                .map(s -> s.split("\\.")[0])
                .collect(Collectors.toSet());
        if (prefixes.isEmpty()) {
            return null;
        }

        ArrayList<Auditorium> res = new ArrayList<>();
        for (String prefix : prefixes) {
            Auditorium aud = new Auditorium();
            String key = prefix + ".name";
            if (auditoriumProps.containsKey(key)) {
                aud.setName(auditoriumProps.get(key));
            }
            key = prefix + ".numberOfSeats";
            if (auditoriumProps.containsKey(key)) {
                aud.setNumberOfSeats(Long.parseLong(auditoriumProps.get(key)));
            }
            key = prefix + ".vipSeats";
            if (auditoriumProps.containsKey(key)) {
                Set<Long> set = Arrays.stream(auditoriumProps.get(key).split(" "))
                        .map(Long::parseLong)
                        .collect(Collectors.toSet());
                aud.setVipSeats(set);
            }
            res.add(aud);
        }
        return res;
    }

    @Bean()
    public static PropertiesFactoryBean userPropsBean() {
        PropertiesFactoryBean bean = new PropertiesFactoryBean();
        bean.setLocation(new ClassPathResource("user.properties"));
        return bean;
    }

    @Resource(name = "userPropsBean")
    private Map<String, String> userProps;

    @Bean()
    public List<User> predefinedUsers() {

        Set<String> prefixes = userProps
                .keySet()
                .stream()
                .map(s -> s.split("\\.")[0])
                .collect(Collectors.toSet());
        if (prefixes.isEmpty()) {
            return null;
        }

        ArrayList<User> res = new ArrayList<>();
        for (String prefix : prefixes) {
            User user = new User();
            String key = prefix + ".firstName";
            if (userProps.containsKey(key)) {
                user.setFirstName(userProps.get(key));
            }
            key = prefix + ".password";
            if (userProps.containsKey(key)) {
                user.setPassword(userProps.get(key));
            }
            key = prefix + ".email";
            if (userProps.containsKey(key)) {
                user.setEmail(userProps.get(key));
            }
            key = prefix + ".roles";
            if (userProps.containsKey(key)) {
                Arrays.stream(userProps.get(key).split(","))
                        .map(UserRole::valueOf)
                        .forEach(user::addRole);
            }
            res.add(user);
        }
        return res;
    }
}
