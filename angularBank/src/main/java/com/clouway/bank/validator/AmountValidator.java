package com.clouway.bank.validator;

import com.clouway.bank.core.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Stanislava Kaukova(sisiivanovva@gmail.com)
 */
public class AmountValidator implements Validator<Double> {

  @Override
  public String validate(Double amount) {
    String message = "";
    if (amount < 0) {
      message = "Amount should be positive number";
    }
    return message;
  }
}
