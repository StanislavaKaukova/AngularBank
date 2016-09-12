package com.clouway.bank.validator;

import com.clouway.bank.core.User;
import com.clouway.bank.core.Validator;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * @author Stanislava Kaukova(sisiivanovva@gmail.com)
 */
public class UserValidatorTest {
  private Validator<User> validator = new UserValidator();

  @Test
  public void userIsValid() throws Exception {
    User user = new User("Ivan", "mail@abv.bg", "pass123");
    String result = validator.validate(user.email, user.password);

    assertThat(result, is(""));
  }

  @Test
  public void emailIsNotInValidFormat() throws Exception {
    User user = new User("Ivan", "ivan.abv.bg", "pass123");
    String result = validator.validate(user.email, user.password);

    assertThat(result, is("The email is wrong"));
  }

  @Test
  public void passwordIsToShort() throws Exception {
    User user = new User("Ivan", "ivan@abv.bg", "pass");
    String result = validator.validate(user.email, user.password);

    assertThat(result, is("The password is wrong!The password should be at least 6 symbols and maximum 18"));
  }

  @Test
  public void passwordIsToLong() throws Exception {
    User user = new User("Ivan", "ivan@abv.bg", "pass123456789012345678");
    String result = validator.validate(user.email, user.password);

    assertThat(result, is("The password is wrong!The password should be at least 6 symbols and maximum 18"));
  }
}
