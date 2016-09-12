package com.clouway.bank.persistence;

import com.clouway.bank.adapter.jdbc.persistence.PersistentSessionRepository;
import com.clouway.bank.core.CurrentTime;
import com.clouway.bank.core.Session;
import com.clouway.bank.core.SessionRepository;
import com.google.common.base.Optional;
import com.google.inject.Provider;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

/**
 * @author Stanislava Kaukova(sisiivanovva@gmail.com)
 */
public class PersistentSessionRepositoryTest {
  @Rule
  public JUnitRuleMockery context = new JUnitRuleMockery();

  private Provider<Connection> provider;
  private PreparedStatement statement;

  @Before
  public void setUp() throws Exception {
    provider = new FakeConnectionProvider();

    statement = provider.get().prepareStatement("truncate table sessions;");
    statement.executeUpdate();
  }

  @After
  public void tearDown() throws Exception {
    statement.close();
  }

  @Test
  public void create() throws Exception {
    final SessionRepository repository = new PersistentSessionRepository(provider);

    long expiredTime = 123456;

    final Session session = new Session("sessionId", "user@domain.com", expiredTime);

    repository.createSession(session);
    Optional<Session> actual = repository.findSessionById(session.sessionId);

    assertThat(actual, is(equalTo(Optional.of(session))));
  }

  @Test
  public void remove() throws Exception {
    final SessionRepository repository = new PersistentSessionRepository(provider);

    long expiredTime = 123456;

    final Session session = new Session("sessionId", "user@domain.com", expiredTime);
    repository.createSession(session);

    repository.remove(session.sessionId);

    Optional<Session> actual = repository.findSessionById(session.sessionId);

    assertThat(actual, is(equalTo(Optional.of(session))));
  }
}
