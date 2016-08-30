package com.clouway.bank.adapter.http;

/**
 * @author Stanislava Kaukova(sisiivanovva@gmail.com)
 */
public class DepositResponseDto {
  public final Double balance;
  public final String message;

  public DepositResponseDto(Double balance, String message) {
    this.balance = balance;
    this.message = message;
  }
}
