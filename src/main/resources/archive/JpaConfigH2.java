package ua.epam.spring.hometask.config;

import com.mchange.v2.c3p0.DriverManagerDataSource;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Properties;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
public class JpaConfigH2 {

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClass("javax.persistence.jdbc.driver");
        dataSource.setJdbcUrl("jdbc:h2:mem:db1;DB_CLOSE_DELAY=-1;MVCC=TRUE");
        dataSource.setUser("111");
        dataSource.setPassword("222");
        return dataSource;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager manager = new JpaTransactionManager();
        manager.setDataSource(dataSource());
        return manager;
    }

    @Bean
    public EntityManagerFactory entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean manager = new LocalContainerEntityManagerFactoryBean();
        manager.setDataSource(dataSource());
        manager.setPackagesToScan(new String[]{"ua.epam.spring.hometask.domain"});
        manager.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        manager.setJpaProperties(JpaProperties());
        manager.setPersistenceUnitName("movieTheater");
        manager.setPersistenceProvider(new HibernatePersistenceProvider());
        manager.afterPropertiesSet();
        EntityManagerFactory factory = manager.getNativeEntityManagerFactory();
        return factory;
    }

    private Properties JpaProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.show_sql", "false");
        properties.setProperty("hibernate.hbm2ddl.auto", "create-drop");
        // TODO add correct dialect
//        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
        properties.setProperty("hibernate.c3p0.min_size", "5");
        properties.setProperty("hibernate.c3p0.max_size", "20");
        properties.setProperty("hibernate.c3p0.timeout", "300");
        properties.setProperty("hibernate.c3p0.max_statements", "50");
        properties.setProperty("hibernate.c3p0.idle_test_period", "3000");
        return properties;
    }
}
