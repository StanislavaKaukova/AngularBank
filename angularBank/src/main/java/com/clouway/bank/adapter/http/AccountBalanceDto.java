package com.clouway.bank.adapter.http;

import com.google.inject.Inject;

/**
 * @author Stanislava Kaukova(sisiivanovva@gmail.com)
 */
public class AccountBalanceDto {
  private final Double balance;
  private final String email;

  @Inject
  public AccountBalanceDto(Double balance, String email) {
    this.balance = balance;
    this.email = email;
  }

  @Override
  public String toString() {
    return "AccountBalanceDto{" +
            "balance=" + balance +
            ", email='" + email + '\'' +
            '}';
  }
}
