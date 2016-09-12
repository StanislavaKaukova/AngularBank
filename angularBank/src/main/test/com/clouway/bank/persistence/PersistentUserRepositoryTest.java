package com.clouway.bank.persistence;

import com.clouway.bank.adapter.jdbc.persistence.PersistentUserRepository;
import com.clouway.bank.core.ConnectionException;
import com.clouway.bank.core.User;
import com.clouway.bank.core.UserRepository;
import com.clouway.bank.utils.DatabaseHelper;
import com.google.inject.Provider;

import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.Is.is;

/**
 * @author Stanislava Kaukova(sisiivanovva@gmail.com)
 */
public class PersistentUserRepositoryTest {
  private Provider<Connection> connectionProvider;

  @Before
  public void setUp() throws Exception {
    connectionProvider = new FakeConnectionProvider();
  }

  @Test
  public void findByEmail() throws Exception {
    UserRepository repository = new PersistentUserRepository(connectionProvider);
    User user = new User("Ivan", "ivan@abv.bg", "1234333333");

    register(user);
    User actual = repository.findByEmail("ivan@abv.bg");

    assertThat(actual, is(user));
  }

  @Test
  public void findNoRegisteredUser() throws Exception {
    UserRepository repository = new PersistentUserRepository(connectionProvider);

    User user = repository.findByEmail("aaaa@abv.bg");

    assertThat(user, is(equalTo(null)));
  }

  private void register(User user) {
    DatabaseHelper databaseHelper = new DatabaseHelper(connectionProvider);
    String query = "Insert into users values(?,?,?)";

    databaseHelper.executeQuery(query, user.name, user.email, user.password);
  }
}
