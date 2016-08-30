package com.clouway.bank.adapter.http;

import com.clouway.bank.core.Account;
import com.clouway.bank.core.AccountRepository;
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

  @Inject
  public UserAccountService(AccountRepository accountRepository) {
    this.accountRepository = accountRepository;
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    resp.setContentType("application/json");

    AccountBalanceDto balanceResponse = new AccountBalanceDto(accountRepository.getBalance("d@abv.bg"), "d@abv.bg");
    resp.getWriter().print(new Gson().toJson(balanceResponse));
  }
}
