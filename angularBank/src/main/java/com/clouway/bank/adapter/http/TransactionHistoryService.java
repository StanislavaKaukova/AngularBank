package com.clouway.bank.adapter.http;

import com.clouway.bank.core.SessionRepository;
import com.clouway.bank.core.Transaction;
import com.clouway.bank.core.TransactionRepository;
import com.clouway.bank.utils.Pager;
import com.clouway.bank.utils.SessionIdFinder;
import com.google.common.base.Strings;
import com.google.gson.Gson;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * @author Stanislava Kaukova(sisiivanovva@gmail.com)
 */
@Singleton
public class TransactionHistoryService extends HttpServlet {
  private final TransactionRepository transactionRepository;
  private int pageSize;
  private final SessionRepository sessionRepository;
  private final SessionIdFinder sessionIdFinder;

  @Inject
  public TransactionHistoryService(TransactionRepository transactionRepository, @Named("pageSize") int pageSize, SessionRepository sessionRepository, SessionIdFinder sessionIdFinder) {
    this.transactionRepository = transactionRepository;
    this.pageSize = pageSize;
    this.sessionRepository = sessionRepository;
    this.sessionIdFinder = sessionIdFinder;
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    String page = req.getParameter("pageNumber");
    int currentPage;

    if (Strings.isNullOrEmpty(page)) {
      currentPage = 1;
    } else {
      currentPage = Integer.valueOf(page);
    }

    String sessionId = sessionIdFinder.findSid(req.getCookies());
    String email = sessionRepository.findUserEmailBySid(sessionId);

    if (email == null) {
      resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      resp.getWriter().print(new Gson().toJson("Can not load current page"));
    }

    Pager pager = new Pager(transactionRepository, pageSize, email);

    List<Transaction> transactions = pager.getPage(currentPage);

    resp.getWriter().print(new Gson().toJson(transactions));
  }
}
