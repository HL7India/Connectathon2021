package com.hl7.india.hapifhirskeleton.fhir;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.server.RestfulServer;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

/**
 * This is the Server class of our application.
 * HAPI provides a class called RestfulServer, which is a specialized Java Servlet.
 * To create a server, we simply create a class which extends RestfulServer.
 */
@WebServlet(urlPatterns = {"/fhir/*"})
@Component
public class SimpleRestfulServer extends RestfulServer {
    private ApplicationContext applicationContext;

    public SimpleRestfulServer(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    /**
     * The initialize method is automatically called when the servlet is starting up, so it can
     * be used to configure the servlet to define resource providers, or set up
     * configuration, interceptors, etc.
     *
     * @throws ServletException
     */
    @Override
    protected void initialize() throws ServletException {

        // Create a context for the appropriate version
        setFhirContext(FhirContext.forR4());
    }
}
