package com.clouway.bank.validator;

import com.clouway.bank.core.Validator;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * @author Stanislava Kaukova(sisiivanovva@gmail.com)
 */
public class AmountValidatorTest {
  private Validator<Double> validator;

  @Before
  public void setUp() throws Exception {
    validator = new AmountValidator();
  }

  @Test
  public void validAmount() throws Exception {
    Double amount = 123.00;

    String actual = validator.validate(amount);
    String expectedErrorMessage = "";

    assertThat(actual, is(expectedErrorMessage));
  }

  @Test
  public void tooLongWholePart() throws Exception {
    Double amount = -1001223234434.00;

    String actual = validator.validate(amount);
    String expectedErrorMessage = "Amount should be positive number";

    assertThat(actual, is(expectedErrorMessage));
  }

  @Test(expected = NumberFormatException.class)
  public void amountIsLetters() throws Exception {
    String amount = "assssaa";

    validator.validate(Double.valueOf(amount));
  }

  @Test
  public void amountIsNegative() throws Exception {
    Double amount = -10.20;

    String actual = validator.validate(amount);
    String expectedErrorMessage = "Amount should be positive number";

    assertThat(actual, is(expectedErrorMessage));
  }
}
