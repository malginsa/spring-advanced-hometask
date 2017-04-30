package ua.epam.spring.hometask.domain;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.xml.bind.annotation.XmlElement;

/**
 * @author Yuriy_Tkach
 */
@MappedSuperclass
public class DomainObject {

    @Id
    @GeneratedValue
    @XmlElement(required = false)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
