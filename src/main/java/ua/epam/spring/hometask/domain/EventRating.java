package ua.epam.spring.hometask.domain;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Yuriy_Tkach and Sergei_Malgin
 */
@XmlEnum(String.class)
@XmlRootElement
public enum EventRating {
    LOW,
    MID,
    HIGH;
}
