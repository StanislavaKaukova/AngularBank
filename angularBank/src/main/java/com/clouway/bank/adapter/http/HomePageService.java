package com.clouway.bank.adapter.http;

import com.clouway.bank.core.SessionRepository;
import com.google.gson.Gson;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Stanislava Kaukova(sisiivanovva@gmail.com)
 */
@Singleton
public class HomePageService extends HttpServlet {
  private SessionRepository sessionRepository;

  @Inject
  public HomePageService(SessionRepository sessionRepository) {
    this.sessionRepository = sessionRepository;
  }

  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    int onlineUsers = sessionRepository.getOnlineUsersCount();
    if (onlineUsers != 0) {
      resp.getWriter().print(new Gson().toJson(onlineUsers));
      return;
    }
    resp.getWriter().print(new Gson().toJson("Error by count online users"));
  }
}
