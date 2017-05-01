package ua.epam.spring.hometask.config;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.ws.transport.http.MessageDispatcherServlet;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

public class WebAppInizializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {

        // MVC dispatcher servlet
        XmlWebApplicationContext xmlAppContext = new XmlWebApplicationContext();
        xmlAppContext.setConfigLocation("/WEB-INF/dispatcher-servlet.xml");
        ServletRegistration.Dynamic dispatcher = servletContext.addServlet(
            "mvc-dispatcher",new DispatcherServlet(xmlAppContext));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");
        dispatcher.setMultipartConfig(new MultipartConfigElement(
            "/tmp", // locations
            20848820, // max-file-size
            418018841, // max-request-size
            1048576 // file-size-threshold
        ));

        // WS dispatcher servlet
//        AnnotationConfigWebApplicationContext annotationAppContext =
//            new AnnotationConfigWebApplicationContext();
//        annotationAppContext.setParent(xmlAppContext);
//        MessageDispatcherServlet servlet =
//            new MessageDispatcherServlet(annotationAppContext);
        MessageDispatcherServlet servlet =
            new MessageDispatcherServlet(xmlAppContext);
        servlet.setTransformSchemaLocations(true);
        ServletRegistration.Dynamic wsDispatcher =
            servletContext.addServlet("ws-dispatcher", servlet);
        wsDispatcher.addMapping("/ws/*");
        wsDispatcher.setLoadOnStartup(2);
    }
}
