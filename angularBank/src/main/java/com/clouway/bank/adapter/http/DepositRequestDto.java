package com.clouway.bank.adapter.http;

/**
 * @author Stanislava Kaukova(sisiivanovva@gmail.com)
 */
public class DepositRequestDto {
  public final Double amount;

  public DepositRequestDto(Double amount) {
    this.amount = amount;
  }
}
