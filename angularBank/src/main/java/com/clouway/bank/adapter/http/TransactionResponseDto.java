package com.clouway.bank.adapter.http;

/**
 * @author Stanislava Kaukova(sisiivanovva@gmail.com)
 */
public class TransactionResponseDto {
  public final Double balance;
  public final String message;

  public TransactionResponseDto(Double balance, String message) {
    this.balance = balance;
    this.message = message;
  }
}
