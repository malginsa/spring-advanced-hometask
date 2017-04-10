package ua.epam.spring.hometask.mvc.viewers;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.web.servlet.view.document.AbstractPdfView;
import ua.epam.spring.hometask.domain.Ticket;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PDFPage extends AbstractPdfView {

    protected void buildPdfDocument(Map model,
                                    Document document,
                                    PdfWriter writer,
                                    HttpServletRequest req,
                                    HttpServletResponse resp)
            throws Exception {
        Set<Ticket> tickets = (Set<Ticket>) model.get("tickets");
        Ticket firstTicket = tickets.iterator().next();
        String userName = firstTicket.getUser().getFirstName();
        String eventName = firstTicket.getEvent().getName();
        LocalDateTime localDateTime = firstTicket.getDateTime();
        document.add(new Paragraph( "List of tickets of " + userName + " for " + eventName + " on " + localDateTime ));
        for(Ticket ticket : tickets) {
            document.add(new Paragraph( "seat=" + ticket.getSeat()
                    + "  price=" + ticket.getPrice()));
        }
    }
}

