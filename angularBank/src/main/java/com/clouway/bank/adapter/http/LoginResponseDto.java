package com.clouway.bank.adapter.http;

/**
 * @author Stanislava Kaukova(sisiivanovva@gmail.com)
 */
public class LoginResponseDto {
  public final String sessionId;
  public final String message;

  public LoginResponseDto(String sessionId, String message) {
    this.sessionId = sessionId;
    this.message = message;
  }

  @Override
  public String toString() {
    return "LoginResponseDto{" +
            "sessionId='" + sessionId + '\'' +
            ", message='" + message + '\'' +
            '}';
  }
}
