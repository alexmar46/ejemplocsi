package com.ejemplo.vaadin.util;

import com.vaadin.Application;
import com.vaadin.terminal.gwt.server.ApplicationServlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
//import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * {@link ApplicationServlet} that autowires and configures the
 * {@link Application} objects it creates using the containing Spring
 * {@link WebApplicationContext}.
 * 
 */
public class AutowiringApplicationServlet extends ApplicationServlet {

    private WebApplicationContext webApplicationContext;

    /**
     * Initialize this servlet.
     *     
     * @throws ServletException if there is no {@link WebApplicationContext}
     * associated with this servlet's context
     */
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        System.out.println("finding containing WebApplicationContext");
        try {
            this.webApplicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(config.getServletContext());
        } catch (IllegalStateException e) {
            throw new ServletException("could not locate containing WebApplicationContext");
        }
    }

    /**
     * Get the containing Spring {@link WebApplicationContext}. This only works
     * after the servlet has been initialized (via {@link #init init()}).
     *     
     * @throws ServletException if the operation fails
     */
    protected final WebApplicationContext getWebApplicationContext() throws ServletException {
        if (this.webApplicationContext == null) {
            throw new ServletException("can't retrieve WebApplicationContext before init() is invoked");
        }
        return this.webApplicationContext;
    }

    /**
     * Get the {@link AutowireCapableBeanFactory} associated with the containing
     * Spring {@link WebApplicationContext}. This only works after the servlet
     * has been initialized (via {@link #init init()}).
     *     
     * @throws ServletException if the operation fails
     */
    protected final AutowireCapableBeanFactory getAutowireCapableBeanFactory() throws ServletException {
        try {
            return getWebApplicationContext().getAutowireCapableBeanFactory();
        } catch (IllegalStateException e) {
            throw new ServletException("containing context " + getWebApplicationContext() + " is not autowire-capable", e);
        }
    }

    /**
     * Create and configure a new instance of the configured application class.
     *     
     *     
     * @param request the triggering {@link HttpServletRequest}
     * @throws ServletException if creation or autowiring fails
     */
    @Override
    protected Application getNewApplication(HttpServletRequest request) throws ServletException {
        Class<? extends Application> cl = null;
        try {
            cl = getApplicationClass();
        } catch (ClassNotFoundException e) {
            System.out.println("failed to find the application class");
        }
        System.out.println("creating new instance of " + cl);
        AutowireCapableBeanFactory beanFactory = getAutowireCapableBeanFactory();
        try {
            return beanFactory.createBean(cl);
        } catch (BeansException e) {
            throw new ServletException("failed to create new instance of " + cl, e);
        }
    }
}
