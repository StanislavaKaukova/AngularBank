package com.clouway.bank.adapter.jdbc.persistence;

import com.clouway.bank.core.AccountRepository;
import com.clouway.bank.core.ConnectionException;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author Stanislava Kaukova(sisiivanovva@gmail.com)
 */
public class JdbcAdapterModule extends AbstractModule {
  @Override
  protected void configure() {
    bind(AccountRepository.class).to(PersistentAccountRepository.class);
  }

  @Provides
  Connection get() {
    try {
      return DriverManager.getConnection("jdbc:postgresql://localhost/bank", "postgres", "clouway.com");
    } catch (SQLException e) {
      throw new ConnectionException("Cannot connect to database");
    }
  }
}
