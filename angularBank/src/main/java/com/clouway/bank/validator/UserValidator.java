package com.clouway.bank.validator;

import com.clouway.bank.core.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Stanislava Kaukova(sisiivanovva@gmail.com)
 */
public class UserValidator implements Validator {

  private final Pattern emailPattern = Pattern.compile(
          "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
  private final Pattern passPattern = Pattern.compile("[A-Za-z0-9]{6,18}");

  @Override
  public String validate(Object object) {
    return null;
  }

  @Override
  public String validate(String email, String password) {
    Matcher userEmail = emailPattern.matcher(email);
    Matcher userPassword = passPattern.matcher(password);

    StringBuilder message = new StringBuilder();
    message.append(validateMatching(userEmail, "The email is wrong"));
    message.append(validateMatching(userPassword, "The password is wrong!The password should be at least 6 symbols and maximum 18"));

    return message.toString();
  }

  private String validateMatching(Matcher matcher, String errorMessage) {
    if (!matcher.matches()) {
      return errorMessage;
    } else {
      return "";
    }
  }
}