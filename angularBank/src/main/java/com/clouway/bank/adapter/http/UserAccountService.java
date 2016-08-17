package com.clouway.bank.adapter.http;

import com.clouway.bank.core.AccountRepository;
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
public class UserAccountService extends HttpServlet {
  private final AccountRepository accountRepository;
  private final SessionIdFinder sessionIdFinder;
  private final SessionRepository sessionRepository;

  @Inject
  public UserAccountService(AccountRepository accountRepository, SessionIdFinder sessionIdFinder, SessionRepository sessionRepository) {
    this.accountRepository = accountRepository;
    this.sessionIdFinder = sessionIdFinder;
    this.sessionRepository = sessionRepository;
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    resp.setContentType("application/json");

    String sessionId = sessionIdFinder.findSid(req.getCookies());
    String userEmail = sessionRepository.findUserEmailBySid(sessionId);

    AccountBalanceDto balanceResponse = new AccountBalanceDto(accountRepository.getBalance(userEmail), userEmail);
    resp.getWriter().print(new Gson().toJson(balanceResponse));
  }
}
