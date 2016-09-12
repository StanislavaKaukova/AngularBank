package com.clouway.bank.core;


/**
 * The implementation of this interface will be used to createSession and retrieve data
 *
 * @author Stanislava Kaukova(sisiivanovva@gmail.com)
 */
public interface UserRepository {
  /**
   * Find registered user
   *
   * @param email email of the user
   */
  User findByEmail(String email);
}
