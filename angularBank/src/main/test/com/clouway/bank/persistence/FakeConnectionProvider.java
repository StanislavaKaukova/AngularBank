package com.clouway.bank.persistence;

import com.clouway.bank.core.ConnectionException;
import com.google.inject.Provider;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author Stanislava Kaukova(sisiivanovva@gmail.com)
 */
public class FakeConnectionProvider implements Provider<Connection> {
  @Override
  public Connection get() {
    try {
      return DriverManager.getConnection("jdbc:postgresql://localhost/test", "postgres", "clouway.com");
    } catch (SQLException e) {
      throw new ConnectionException("Cannot connect to database");
    }
  }
}
