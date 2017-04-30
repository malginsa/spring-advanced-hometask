package ua.epam.spring.hometask.domain;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlEnum(String.class)
public enum UserRole {

    ROLE_REGISTERED_USER,
    ROLE_BOOKING_MANAGER;
}
