package com.clouway.bank.adapter.http;

import com.clouway.bank.core.Account;
import com.clouway.bank.core.AccountRepository;
import com.clouway.bank.core.User;
import com.clouway.bank.core.UserRepository;
import com.clouway.bank.core.Validator;
import com.google.common.base.Strings;
import com.google.gson.Gson;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author Stanislava Kaukova(sisiivanovva@gmail.com)
 */
@Singleton
public class RegisterService extends HttpServlet {
  private final Validator<User> validator;
  private final UserRepository userRepository;
  private final AccountRepository accountRepository;

  @Inject
  public RegisterService(Validator<User> validator, UserRepository userRepository, AccountRepository accountRepository) {
    this.validator = validator;
    this.userRepository = userRepository;
    this.accountRepository = accountRepository;
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    ServletInputStream servletInputStream = req.getInputStream();
    BufferedReader reader = new BufferedReader(new InputStreamReader(servletInputStream));
    RegisterRequestDto requestDto = new Gson().fromJson(reader, RegisterRequestDto.class);

    User user = requestDto.user;
    if (user == null) {
      resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      resp.getWriter().print(new Gson().toJson("Input field are empty"));
      return;
    }

    String repeatedPassword = requestDto.confirmPassword;
    String messages = String.format("%s%s", validator.validate(requestDto.user), validator.validateEquality(user.password, repeatedPassword));

    if (!Strings.isNullOrEmpty(messages)) {
      resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      resp.getWriter().print(new Gson().toJson(messages));
      return;
    }

    if (userRepository.findByEmail(user.email) != null) {
      resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      resp.getWriter().print(new Gson().toJson("User with this email already exist"));

    } else {
      userRepository.register(user);
      accountRepository.createAccount(new Account(user.email, 0.0));
      resp.getWriter().print(new Gson().toJson("You are registered successfully!!!"));
    }
  }
}