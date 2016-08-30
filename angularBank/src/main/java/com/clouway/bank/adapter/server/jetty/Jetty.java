package com.clouway.bank.adapter.server.jetty;

import com.clouway.bank.guiceModule.BankModule;
import com.clouway.bank.guiceModule.BankServletModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceFilter;
import com.google.inject.servlet.GuiceServletContextListener;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.webapp.WebAppContext;

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
    WebAppContext context = new WebAppContext();
    context.setResourceBase("src/main/webapp");
    context.setContextPath("/");

    context.addFilter(GuiceFilter.class, "/*", EnumSet.allOf(DispatcherType.class));
    context.addServlet(DefaultServlet.class, "/");

    context.addEventListener(new GuiceServletContextListener() {
      @Override
      protected Injector getInjector() {
        return Guice.createInjector(new BankModule(), new BankServletModule());
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
