package com.clouway.bank.adapter.jdbc.persistence;

import com.clouway.bank.core.RowGetter;
import com.clouway.bank.core.User;
import com.clouway.bank.core.UserRepository;
import com.clouway.bank.utils.DatabaseHelper;
import com.google.inject.Inject;
import com.google.inject.Provider;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Stanislava Kaukova(sisiivanovva@gmail.com)
 */
public class PersistentUserRepository implements UserRepository {
  private Provider<Connection> connectionProvider;

  @Inject
  public PersistentUserRepository(Provider<Connection> connectionProvider) {
    this.connectionProvider = connectionProvider;
  }

  @Override
  public void register(User user) {
    DatabaseHelper databaseHelper = new DatabaseHelper(connectionProvider);
    String query = "INSERT INTO users VALUES (?,?,?)";

    databaseHelper.executeQuery(query, user.name, user.email, user.password);
  }

  @Override
  public User findByEmail(String email) {
    DatabaseHelper databaseHelper = new DatabaseHelper(connectionProvider);
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
