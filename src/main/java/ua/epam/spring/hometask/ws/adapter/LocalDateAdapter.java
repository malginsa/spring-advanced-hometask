package ua.epam.spring.hometask.ws.adapter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.xml.bind.annotation.adapters.XmlAdapter;

public class LocalDateAdapter extends XmlAdapter<String, LocalDate> {

    @Override
    public LocalDate unmarshal(String string) throws Exception {
        return LocalDate.parse(string, DateTimeFormatter.ISO_LOCAL_DATE);
    }

    @Override
    public String marshal(LocalDate ld) throws Exception {
        return ld.format(DateTimeFormatter.ISO_LOCAL_DATE);
    }
}
