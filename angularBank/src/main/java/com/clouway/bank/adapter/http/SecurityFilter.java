package com.clouway.bank.adapter.http;

import com.clouway.bank.core.CurrentTime;
import com.clouway.bank.core.Session;
import com.clouway.bank.core.SessionRepository;
import com.clouway.bank.utils.SessionIdFinder;
import com.google.common.base.Optional;
import com.google.gson.Gson;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Stanislava Kaukova(sisiivanovva@gmail.com)
 */
@Singleton
public class SecurityFilter implements Filter {
  private final SessionRepository sessionRepository;
  private final CurrentTime time;
  private final SessionIdFinder sessionIdFinder;

  @Inject
  public SecurityFilter(SessionRepository sessionRepository, CurrentTime time, SessionIdFinder sessionIdFinder) {
    this.sessionRepository = sessionRepository;
    this.time = time;
    this.sessionIdFinder = sessionIdFinder;
  }

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {

  }

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
    HttpServletRequest request = (HttpServletRequest) servletRequest;
    HttpServletResponse response = (HttpServletResponse) servletResponse;
    final String uri = request.getRequestURI();

    String sessionId = sessionIdFinder.findSid(request.getCookies());

    Optional<Session> currentSession = sessionRepository.findSessionById(sessionId);

    boolean isLoginPage = uri.contains("/login");
    boolean isRegisterPage = uri.contains("/register");

    if (isSessionExpired(currentSession)) {
      sessionRepository.remove(sessionId);
    }

    if (isLoginPage && isAuthorized(currentSession)) {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      response.getWriter().print(new Gson().toJson("You already logged in!"));
      return;
    }
    if (isSessionExpired(currentSession) && !isLoginPage && isAuthorized(currentSession)) {
      Session session = currentSession.get();
      sessionRepository.update(session);
      return;
    }

    if (isRegisterPage || isLoginPage || isAuthorized(currentSession)) {
      filterChain.doFilter(request, response);

    } else {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      response.getWriter().print(new Gson().toJson("You should log in!"));
    }

  }

  @Override
  public void destroy() {

  }

  private boolean isAuthorized(Optional<Session> currentSession) {
    return currentSession.isPresent();
  }

  private boolean isSessionExpired(Optional<Session> currentSession) {
    return currentSession.isPresent() && currentSession.get().expirationTime < time.getCurrentTime();
  }
}