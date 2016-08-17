package com.clouway.bank.adapter.http;

import com.clouway.bank.core.AccountRepository;
import com.clouway.bank.core.SessionRepository;
import com.clouway.bank.core.TransactionRepository;
import com.clouway.bank.core.Validator;
import com.clouway.bank.utils.SessionIdFinder;
import com.google.common.base.Strings;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;

/**
 * @author Stanislava Kaukova(sisiivanovva@gmail.com)
 */
@Singleton
public class DepositService extends HttpServlet {
  private final Validator validator;
  private final AccountRepository accountRepository;
  private final TransactionRepository transactionRepository;
  private final SessionRepository sessionRepository;
  private final SessionIdFinder sessionIdFinder;
  private Gson json;

  @Inject
  public DepositService(@Named("amountValidator") Validator validator, AccountRepository accountRepository, TransactionRepository transactionRepository, SessionRepository sessionRepository, SessionIdFinder sessionIdFinder, Gson json) {
    this.validator = validator;
    this.accountRepository = accountRepository;
    this.transactionRepository = transactionRepository;
    this.sessionRepository = sessionRepository;
    this.sessionIdFinder = sessionIdFinder;
    this.json = json;
  }

  @Override
  public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    resp.setContentType("application/json");

    TransactionRequestDto requestDto;

    String sessionId = sessionIdFinder.findSid(req.getCookies());
    String userEmail = sessionRepository.findUserEmailBySid(sessionId);

    String message;
    try {
      ServletInputStream inputStream = req.getInputStream();

      BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
      requestDto = json.fromJson(reader, TransactionRequestDto.class);

    } catch (JsonSyntaxException e) {
      message = "The input field should be number!!!";

      resp.setStatus(HttpServletResponse.SC_BAD_GATEWAY);
      resp.getWriter().print(json.toJson(new TransactionResponseDto(accountRepository.getBalance(userEmail), message)));
      return;
    }

    if (requestDto.amount == null) {
      resp.setStatus(HttpServletResponse.SC_BAD_GATEWAY);
      resp.getWriter().print(json.toJson(new TransactionResponseDto(accountRepository.getBalance(userEmail), "Input field is empty, please fill it")));
      return;
    }

    message = validator.validate(requestDto.amount);

    if (!Strings.isNullOrEmpty(message)) {
      resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
      resp.getWriter().print(json.toJson(new TransactionResponseDto((accountRepository.getBalance(userEmail)), message)));
    } else {

      accountRepository.deposit(userEmail, requestDto.amount);
      Double currentBalance = accountRepository.getBalance(userEmail);
      currentBalance = Double.parseDouble(new DecimalFormat("##.##").format(currentBalance));

      transactionRepository.updateHistory(userEmail, "deposit", requestDto.amount);
      message = "Success! You added " + requestDto.amount + " to your balance";

      resp.getWriter().print(json.toJson(new TransactionResponseDto(currentBalance, message)));
    }
  }
}
