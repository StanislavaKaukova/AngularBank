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
  public void userNameIsValid() throws Exception {
    User user = new User("Ivan", "mail@abv.bg", "pass123");
    String result = validator.validate(user);

    assertThat(result, is(""));
  }

  @Test
  public void userNameContainsDigits() throws Exception {
    User user = new User("Ivan66", "ivan@abv.bg", "pass123");
    String result = validator.validate(user);

    assertThat(result, is("The name should be between 1 and 15 letters"));
  }

  @Test
  public void userNameIsTooLong() throws Exception {
    User user = new User("ThisNameIsToLong", "ivan@abv.bg", "pass123");
    String result = validator.validate(user);

    assertThat(result, is("The name should be between 1 and 15 letters"));
  }

  @Test
  public void emailIsNotInValidFormat() throws Exception {
    User user = new User("Ivan", "ivan.abv.bg", "pass123");
    String result = validator.validate(user);

    assertThat(result, is("The email format should be like example@domain.com"));
  }

  @Test
  public void passwordIsTooShort() throws Exception {
    User user = new User("Ivan", "ivan@abv.bg", "pass");
    String result = validator.validate(user);

    assertThat(result, is("The password should be at least 6 symbols"));
  }

  @Test
  public void passwordIsTooLong() throws Exception {
    User user = new User("Ivan", "ivan@abv.bg", "pass123456789012345678");
    String result = validator.validate(user);

    assertThat(result, is("The password should be at least 6 symbols"));
  }

  @Test
  public void objectsAreEquals() throws Exception {
    String object1 = "object1";
    String object2 = "object1";
    String result = validator.validateEquality(object1, object2);

    assertThat(result, is(""));
  }
  @Test
  public void objectsAreNotEquals() throws Exception {
    String object1 = "object1";
    String object2 = "object2";
    String result = validator.validateEquality(object1, object2);

    assertThat(result, is("The passwords are not equals!"));
  }
}
