package com.clouway.bank.core;

/**
 * The implementation of this interface will be used for validation
 *
 * @author Stanislava Kaukova(sisiivanovva@gmail.com)
 */
public interface Validator<T> {
  /**
   * Will check the user
   *
   * @param object inspected object
   * @return result message from validation
   */
  String validate(T object);

  /**
   * @param email
   * @param password
   * @return
   */
  String validate(String email, String password);

  /**
   * Will compare two objects
   *
   * @param object1 object 1
   * @param object2 object 2
   * @return report from validation
   */
  String validateEquality(String object1, String object2);
}
