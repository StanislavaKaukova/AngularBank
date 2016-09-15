package com.clouway.bank.validator;

import com.clouway.bank.core.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.clouway.bank.core.User;
import com.clouway.bank.core.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Stanislava Kaukova(sisiivanovva@gmail.com)
 */
public class UserValidator implements Validator<User> {
  private final Pattern emailPattern = Pattern.compile(
          "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
  private final Pattern namePattern = Pattern.compile("[a-zA-Z]{3,15}");
  private final Pattern passPattern = Pattern.compile("[A-Za-z0-9]{6,18}");

  @Override
  public String validate(String email, String password) {
    Matcher userEmail = emailPattern.matcher(email);
    Matcher userPassword = passPattern.matcher(password);

    StringBuilder message = new StringBuilder();
    message.append(validateMatching(userEmail, "The email is wrong"));
    message.append(validateMatching(userPassword, "The password is wrong!The password should be at least 6 symbols and maximum 18"));

    return message.toString();
  }


  @Override
  public String validate(User user) {
    StringBuilder message = new StringBuilder();

    boolean hasEmptyFields = user.name == null || user.email == null || user.password == null;
    if (hasEmptyFields) {
      message.append("All fields are required!");
      return message.toString();
    }

    Matcher email = emailPattern.matcher(user.email);
    Matcher name = namePattern.matcher(user.name);
    Matcher password = passPattern.matcher(user.password);

    message.append(validateMatching(name, "The name should be between 1 and 15 letters"));
    message.append(validateMatching(email, "The email format should be like example@domain.com"));
    message.append(validateMatching(password, "The password should be at least 6 symbols"));

    return message.toString();
  }

  @Override
  public String validateEquality(String object1, String object2) {
    String message;
    if (!object1.equals(object2)) {
      message = "The passwords are not equals!";
      return message;
    }
    return "";
  }

  private String validateMatching(Matcher matcher, String errorMessage) {
    if (!matcher.matches()) {
      return errorMessage;
    } else {
      return "";
    }
  }
}