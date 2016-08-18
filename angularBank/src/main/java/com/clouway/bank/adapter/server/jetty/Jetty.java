package com.clouway.bank.adapter.server.jetty;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceFilter;
import com.google.inject.servlet.GuiceServletContextListener;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;

import javax.servlet.DispatcherType;
import java.util.EnumSet;

/**
 * @author Stanislava Kaukova(sisiivanovva@gmail.com)
 */
public class Jetty {
  private final Server server;

  public Jetty(int port) {
    this.server = new Server(port);
  }

  public void start() {
    ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
    context.setContextPath("/");

    context.addFilter(GuiceFilter.class, "/*", EnumSet.allOf(DispatcherType.class));
    context.addServlet(DefaultServlet.class, "/");

    context.addEventListener(new GuiceServletContextListener() {
      @Override
      protected Injector getInjector() {
        return Guice.createInjector();
      }
    });

    server.setHandler(context);
    try {
      server.start();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
