package com.clouway.bank.adapter.jdbc.persistence;

import com.clouway.bank.core.*;
import com.clouway.bank.utils.DatabaseHelper;
import com.google.inject.Inject;
import com.google.inject.Provider;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Stanislava Kaukova(sisiivanovva@gmail.com)
 */
public class PersistentTransactionRepository implements TransactionRepository {
  private final AccountRepository accountRepository;
  private final Provider<Connection> provider;
  private final CurrentDate date;

  @Inject
  public PersistentTransactionRepository(AccountRepository accountRepository, Provider<Connection> provider, CurrentDate date) {
    this.accountRepository = accountRepository;
    this.provider = provider;
    this.date = date;
  }

  @Override
  public List<Transaction> getTransactions(String email, int limit, int offset) {
    List<Transaction> transactions = new ArrayList<>();

    try (PreparedStatement statement = provider.get().prepareStatement("SELECT * FROM transactions WHERE email=? LIMIT " + limit + " OFFSET " + offset + "")) {
      statement.setString(1, email);

      ResultSet resultSet = statement.executeQuery();

      while (resultSet.next()) {
        Long date = resultSet.getLong(1);
        String userEmail = resultSet.getString("email");
        String operation = resultSet.getString("operation");
        Double processingAmount = resultSet.getDouble("amount");
        Double balance = resultSet.getDouble("currentBalance");
        transactions.add(new Transaction(date, userEmail, operation, processingAmount, balance));
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw new ConnectionException("Cannot connect to database");
    }

    return transactions;
  }

  @Override
  public int getNumberOfRecords() {
    DatabaseHelper databaseHelper = new DatabaseHelper(provider);
    String query = " select COUNT (*) from transactions ";

    return (int) databaseHelper.fetchRow(query, new RowGetter() {
      @Override
      public Object getRows(ResultSet resultSet) throws SQLException {
        return resultSet.getInt(1);
      }
    });
  }

  @Override
  public void updateHistory(String email, String operation, Double amount) {
    Account account = accountRepository.findByEmail(email);

    try (PreparedStatement statement = provider.get().prepareStatement("INSERT INTO transactions VALUES (?,?,?,?,?)")) {
      statement.setLong(1, date.getCurrentDate());
      statement.setString(2, email);
      statement.setString(3, operation);
      statement.setDouble(4, amount);
      statement.setDouble(5, account.getBalance());

      statement.executeUpdate();
    } catch (SQLException e) {
      new ConnectionException("Cannot connect to database");
    }
  }
}
