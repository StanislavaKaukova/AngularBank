package com.clouway.bank.adapter.http;

import com.clouway.bank.core.CurrentTime;
import com.clouway.bank.core.IdGenerator;
import com.clouway.bank.core.Session;
import com.clouway.bank.core.SessionRepository;
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
public class LoginService extends HttpServlet {
  private final UserRepository userRepository;
  private final SessionRepository sessionRepository;
  private final Validator<User> validator;
  private final IdGenerator generator;
  private final CurrentTime time;
  private Gson json;

  @Inject
  public LoginService(UserRepository userRepository, SessionRepository sessionRepository, Validator<User> validator, IdGenerator generator, CurrentTime time, Gson json) {
    this.userRepository = userRepository;
    this.sessionRepository = sessionRepository;
    this.validator = validator;
    this.generator = generator;
    this.time = time;
    this.json = json;
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    resp.setContentType("application/json");

    ServletInputStream inputStream = req.getInputStream();
    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
    LoginRequestDto requestDto = new Gson().fromJson(reader, LoginRequestDto.class);

    if (Strings.isNullOrEmpty(requestDto.email) || Strings.isNullOrEmpty(requestDto.password)) {
      resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      resp.getWriter().print(json.toJson("Input field is empty, please fill it"));
      return;
    }

    String email = requestDto.email;
    String password = requestDto.password;
    String message = validator.validate(email, password);

    if (!Strings.isNullOrEmpty(message)) {
      resp.setStatus(HttpServletResponse.SC_FOUND);
      resp.getWriter().print(json.toJson(message));
      return;
    }

    User user = userRepository.findByEmail(email);
    if (user == null || !user.password.equals(password)) {
      message = "You should register first";

      resp.setStatus(HttpServletResponse.SC_FOUND);
      resp.getWriter().print(json.toJson(message));

    } else {
      String sessionId = generator.generateId();

      Session session = new Session(sessionId, email, time.expirationTime());
      sessionRepository.createSession(session);

      resp.getWriter().print(json.toJson(new LoginResponseDto(sessionId, "Success")));
    }
  }
}