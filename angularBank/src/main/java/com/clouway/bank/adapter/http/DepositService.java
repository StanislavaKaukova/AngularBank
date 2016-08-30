package com.clouway.bank.adapter.http;

import com.clouway.bank.core.AccountRepository;
import com.clouway.bank.core.Validator;
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
  private Gson json;

  @Inject
  public DepositService(@Named("amountValidator") Validator validator, AccountRepository accountRepository, Gson json) {
    this.validator = validator;
    this.accountRepository = accountRepository;
    this.json = json;
  }

  @Override
  public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    resp.setContentType("application/json");

    DepositRequestDto requestDto;

    String userEmail = "d@abv.bg";
    String message;
    try {
      ServletInputStream inputStream = req.getInputStream();

      BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
      requestDto = json.fromJson(reader, DepositRequestDto.class);

    } catch (JsonSyntaxException e) {
      message = "The input field should be number!!!";

      resp.setStatus(HttpServletResponse.SC_BAD_GATEWAY);
      resp.getWriter().print(json.toJson(new DepositResponseDto(accountRepository.getBalance(userEmail), message)));
      return;
    }

    if (requestDto.amount == null) {
      resp.setStatus(HttpServletResponse.SC_BAD_GATEWAY);
      resp.getWriter().print(json.toJson(new DepositResponseDto(accountRepository.getBalance(userEmail), "Input field is empty, please fill it")));
      return;
    }

    message = validator.validate(requestDto.amount);

    if (!Strings.isNullOrEmpty(message)) {
      resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
      resp.getWriter().print(json.toJson(new DepositResponseDto((accountRepository.getBalance(userEmail)), message)));
    } else {

      accountRepository.deposit(userEmail, requestDto.amount);

      Double currentBalance = accountRepository.getBalance(userEmail);
      currentBalance = Double.parseDouble(new DecimalFormat("##.##").format(currentBalance));

      message = "Success! You added " + requestDto.amount + " to your balance";

      resp.getWriter().print(json.toJson(new DepositResponseDto(currentBalance, message)));
    }
  }
}
