package com.clouway.bank.adapter.jdbc.persistence;

import com.clouway.bank.core.RowGetter;
import com.clouway.bank.core.UserRepository;
import com.clouway.bank.utils.DatabaseHelper;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.clouway.bank.core.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Stanislava Kaukova(sisiivanovva@gmail.com)
 */
public class PersistentUserRepository implements UserRepository {
  private final Provider<Connection> provider;

  @Inject
  public PersistentUserRepository(Provider<Connection> provider) {
    this.provider = provider;
  }

  @Override
  public User findByEmail(String email) {
    DatabaseHelper databaseHelper = new DatabaseHelper(provider);
    String userEmail = "'" + email + "'";

    return (User) databaseHelper.fetchRow("SELECT * FROM users WHERE email=" + userEmail, new RowGetter() {
      @Override
      public User getRows(ResultSet resultSet) {
        try {
          String name = resultSet.getString("name");
          String password = resultSet.getString("password");
          return new User(name, email, password);
        } catch (SQLException e) {
          e.printStackTrace();
        }
        return null;
      }
    });
  }
}
