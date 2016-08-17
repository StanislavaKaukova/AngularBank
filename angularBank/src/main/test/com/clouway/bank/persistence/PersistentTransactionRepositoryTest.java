package com.clouway.bank.persistence;

import com.clouway.bank.adapter.jdbc.persistence.PersistentTransactionRepository;
import com.clouway.bank.core.Account;
import com.clouway.bank.core.AccountRepository;
import com.clouway.bank.core.CurrentDate;
import com.clouway.bank.core.Transaction;
import com.clouway.bank.core.TransactionRepository;
import com.clouway.bank.utils.DatabaseHelper;
import com.google.common.collect.Lists;
import com.google.inject.Provider;
import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * @author Stanislava Kaukova(sisiivanovva@gmail.com)
 */
public class PersistentTransactionRepositoryTest {
  @Rule
  public JUnitRuleMockery context = new JUnitRuleMockery();

  @Mock
  private CurrentDate date;

  @Mock
  private AccountRepository accountRepository;

  private Provider<Connection> provider;
  private PreparedStatement statement;

  private TransactionRepository transactionRepository;

  private final Account account = new Account("user@domain.com", 50.00);
  private final long currentDate = 1212121212;

  @Before
  public void setUp() throws Exception {
    provider = new FakeConnectionProvider();

    transactionRepository = new PersistentTransactionRepository(accountRepository, provider, date);

    statement = provider.get().prepareStatement("truncate table transactions;");
    statement.executeUpdate();
  }

  @After
  public void tearDown() throws Exception {
    statement.close();
  }

  @Test
  public void getTransactions() throws Exception {
    final long currentDate = 123123123;
    Double depositAmount = 20.00;
    Double withdrawAmount = 20.00;

    final Transaction transaction1 = new Transaction(currentDate, account.email, "deposit", depositAmount, 50.00);
    final Transaction transaction2 = new Transaction(currentDate, account.email, "withdraw", withdrawAmount, 50.00);

    makeTransaction(transaction1);
    makeTransaction(transaction2);
    List<Transaction> expected = Lists.newArrayList(transaction1, transaction2);
    List<Transaction> actual = transactionRepository.getTransactions(account.email, 2, 0);

    assertThat(actual, is(expected));
  }

  @Test
  public void getNumberOfRecords() throws Exception {
    final long currentDate = 123123123;
    Double depositAmount = 20.00;
    Double withdrawAmount = 20.00;

    final Transaction transaction1 = new Transaction(currentDate, account.email, "deposit", depositAmount, 50.00);
    final Transaction transaction2 = new Transaction(currentDate, account.email, "withdraw", withdrawAmount, 50.00);

    makeTransaction(transaction1);
    makeTransaction(transaction2);

    int actual = transactionRepository.getNumberOfRecords();

    assertThat(actual, is(2));

  }

  private void makeTransaction(Transaction transaction) {
    DatabaseHelper databaseHelper = new DatabaseHelper(provider);
    String query = "Insert into transactions values(?,?,?,?,?)";

    databaseHelper.executeQuery(query, transaction.date, transaction.email, transaction.operation, transaction.processingAmount, transaction.currentAmount);
  }
}
