package com.clouway.bank.adapter.http;

/**
 * @author Stanislava Kaukova(sisiivanovva@gmail.com)
 */
public class LoginRequestDto {
  public final String email;
  public final String password;

  public LoginRequestDto(String email, String password) {
    this.email = email;
    this.password = password;
  }

  @Override
  public String toString() {
    return "LoginRequestDto{" +
            "name='" + email + '\'' +
            ", password='" + password + '\'' +
            '}';
  }
}
