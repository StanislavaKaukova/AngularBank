package com.clouway.bank.adapter.http;

/**
 * @author Stanislava Kaukova(sisiivanovva@gmail.com)
 */
public class TransactionRequestDto {
  public final Double amount;

  public TransactionRequestDto(Double amount) {
    this.amount = amount;
  }
}
