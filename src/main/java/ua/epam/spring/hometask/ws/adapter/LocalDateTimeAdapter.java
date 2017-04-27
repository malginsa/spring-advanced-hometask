package ua.epam.spring.hometask.ws.adapter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.xml.bind.annotation.adapters.XmlAdapter;

public class LocalDateTimeAdapter
    extends XmlAdapter<String, LocalDateTime> {

    @Override
    public LocalDateTime unmarshal(String string) throws Exception {
        return LocalDateTime.parse(string, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    @Override
    public String marshal(LocalDateTime ldt) throws Exception {
        return ldt.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }
}
