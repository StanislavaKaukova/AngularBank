package com.clouway.bank.adapter.http;

import com.clouway.bank.core.User;

/**
 * @author Stanislava Kaukova(sisiivanovva@gmail.com)
 */
public class RegisterRequestDto {
  public final User user;
  public final String confirmPassword;

  public RegisterRequestDto(User user, String confirmPassword) {
    this.user = user;
    this.confirmPassword = confirmPassword;
  }

  @Override
  public String toString() {
    return "RegisterRequestDto{" +
            "user=" + user +
            '}';
  }
}
