package com.clouway.bank.utils;

import com.google.inject.Inject;

import javax.servlet.http.Cookie;

/**
 * @author Stanislava Kaukova(sisiivanovva@gmail.com)
 */
public class SessionIdFinder {
  private final String sidCookie;

  @Inject
  public SessionIdFinder(String sidCookie) {
    this.sidCookie = sidCookie;
  }

  public String findSid(Cookie[] cookies) {
    String sessionId = "";
    if (cookies == null) {
      return null;
    }
    for (Cookie each : cookies) {
      if (each.getName().equals(sidCookie)) {
        sessionId = each.getValue();
      }
    }
    return sessionId;
  }
}

