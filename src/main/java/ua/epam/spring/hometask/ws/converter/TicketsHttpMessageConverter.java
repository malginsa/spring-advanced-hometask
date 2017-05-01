package ua.epam.spring.hometask.ws.converter;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import ua.epam.spring.hometask.domain.Ticket;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class TicketsHttpMessageConverter implements HttpMessageConverter<Collection<Ticket>> {

    @Override
    public boolean canRead(Class<?> clazz, MediaType mediaType) {
        return false;
    }

    @Override
    public boolean canWrite(Class<?> clazz, MediaType mediaType) {
        return true;
    }

    @Override
    public List<MediaType> getSupportedMediaTypes() {
        return Arrays.asList(MediaType.APPLICATION_PDF);
    }

    @Override
    public Collection<Ticket> read(Class<? extends Collection<Ticket>> clazz, HttpInputMessage inputMessage)
        throws IOException, HttpMessageNotReadableException {
        return null;
    }

    @Override
    public void write(Collection<Ticket> tickets, MediaType contentType, HttpOutputMessage outputMessage)
        throws IOException, HttpMessageNotWritableException {

        Document document = new Document();
        Ticket firstTicket = tickets.iterator().next();
        String userName = firstTicket.getUser().getFirstName();
        String eventName = firstTicket.getEvent().getName();
        LocalDateTime localDateTime = firstTicket.getDateTime();

        try {
            document.add(new Paragraph(
                "List of tickets of " + userName + " for " + eventName + " on " + localDateTime ));
            for(Ticket ticket : tickets) {
                document.add(new Paragraph( "seat=" + ticket.getSeat()
                                            + "  price=" + ticket.getPrice()));
            }
        } catch (DocumentException e) {
            throw new HttpMessageNotReadableException(
                "Error occured during pdf-document creation");
        }
        document.close();
    }
}
