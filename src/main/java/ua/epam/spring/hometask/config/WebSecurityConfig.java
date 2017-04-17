package ua.epam.spring.hometask.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new StandardPasswordEncoder();
    }

    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/")
                    .hasAuthority("ROLE_REGISTERED_USER")
                .antMatchers("/getTickets**")
                    .hasAuthority("ROLE_BOOKING_MANAGER")
                .anyRequest()
                    .authenticated()
                .and()
                .formLogin()
                    .loginPage("/login?error=false")
                        .permitAll()
                    .loginProcessingUrl("/login")
                    .usernameParameter("username")
                    .passwordParameter("password")
                    .defaultSuccessUrl("/mainMenu")
                    .failureUrl("/login?error=true")
                .and()
                .logout()
                    .logoutSuccessUrl("/login?error=false")
                    .invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID")
                .and()
                .rememberMe()
                    .rememberMeParameter("remember-me")
                    .key("uniqueAndSecret")
                    .useSecureCookie(true)
                .and()
                .csrf()
                    .disable();
    }

}
