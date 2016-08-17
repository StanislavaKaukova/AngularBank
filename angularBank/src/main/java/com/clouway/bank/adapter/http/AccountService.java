package com.clouway.bank.adapter.http;

import com.clouway.bank.core.SessionRepository;
import com.clouway.bank.utils.SessionIdFinder;
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
public class AccountService extends HttpServlet {
  private final SessionIdFinder sessionIdFinder;
  private final SessionRepository sessionRepository;

  @Inject
  public AccountService(SessionIdFinder sessionIdFinder, SessionRepository sessionRepository) {
    this.sessionIdFinder = sessionIdFinder;
    this.sessionRepository = sessionRepository;
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    String sessionId = sessionIdFinder.findSid(req.getCookies());
    String email = sessionRepository.findUserEmailBySid(sessionId);

    resp.getWriter().print(new Gson().toJson(email));
  }
}
