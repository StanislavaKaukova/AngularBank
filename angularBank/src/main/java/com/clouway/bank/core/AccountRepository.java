package com.clouway.bank.core;

/**
 * @author Stanislava Kaukova(sisiivanovva@gmail.com)
 */

/**
 * The implementation of this interface will be used to save and retrieve data for account
 *
 * @author Stanislava Kaukova(sisiivanovva@gmail.com)
 */
public interface AccountRepository {
  /**
   * Will be used for creating account
   *
   * @param account created account
   */
  void createAccount(Account account);

  /**
   * Will be find account by user email
   *
   * @param email user email
   * @return account
   */
  Account findByEmail(String email);

  /**
   * Will be added amount to user
   *
   * @param email  user email
   * @param amount added amount
   */
  void deposit(String email, Double amount);

  /**
   * Will be get current balance
   *
   * @param email current user
   * @return current balance
   */
  Double getBalance(String email);

  /**
   * Will be withdraw from account
   * @param userEmail current user
   * @param amount cash out
   */
  void withdraw(String userEmail, Double amount);
}
