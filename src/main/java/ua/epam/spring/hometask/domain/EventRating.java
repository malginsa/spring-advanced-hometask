package ua.epam.spring.hometask.domain;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

/**
 * @author Yuriy_Tkach
 */
@XmlEnum
@XmlType
public enum EventRating {
    LOW,
    MID,
    HIGH;
}
