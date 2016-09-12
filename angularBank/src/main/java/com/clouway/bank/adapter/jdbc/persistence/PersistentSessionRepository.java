package com.clouway.bank.adapter.jdbc.persistence;

import com.clouway.bank.core.RowGetter;
import com.clouway.bank.core.Session;
import com.clouway.bank.core.SessionRepository;
import com.clouway.bank.utils.DatabaseHelper;
import com.google.common.base.Optional;
import com.google.inject.Inject;
import com.google.inject.Provider;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Stanislava Kaukova(sisiivanovva@gmail.com)
 */
public class PersistentSessionRepository implements SessionRepository {
  private final Provider<Connection> provider;

  @Inject
  public PersistentSessionRepository(Provider<Connection> provider) {
    this.provider = provider;
  }

  @Override
  public void createSession(Session session) {
    DatabaseHelper databaseHelper = new DatabaseHelper(provider);
    String query = "INSERT into sessions VALUES(?,?,?)";

    databaseHelper.executeQuery(query, session.sessionId, session.email, session.expirationTime);
  }

  @Override
  public Optional<Session> findSessionById(String id) {
    DatabaseHelper databaseHelper = new DatabaseHelper(provider);
    String sessionId = "'" + id + "'";

    return (Optional<Session>) databaseHelper.fetchRow("SELECT * FROM sessions WHERE id=" + sessionId, new RowGetter() {
      @Override
      public Optional<Session> getRows(ResultSet resultSet) {
        try {
          String email = resultSet.getString("email");
          long expirationTime = resultSet.getLong("expirationTime");
          Session session = new Session(id, email, expirationTime);

          return Optional.of(session);
        } catch (SQLException e) {
        }
        return Optional.absent();
      }
    });
  }

  @Override
  public void remove(String id) {
    DatabaseHelper databaseHelper = new DatabaseHelper(provider);
    String query = "DELETE from sessions WHERE id=" + "'" + id + "'";

    databaseHelper.executeQuery(query);
  }

  @Override
  public String findUserEmailBySid(String sessionId) {
    DatabaseHelper databaseHelper = new DatabaseHelper(provider);
    String id = "'" + sessionId + "'";

    return (String) databaseHelper.fetchRow("SELECT email from sessions WHERE id=" + id, new RowGetter() {
      @Override
      public String getRows(ResultSet resultSet) throws SQLException {
        return resultSet.getString("email");
      }
    });
  }

  @Override
  public void update(Session session) {
    DatabaseHelper databaseHelper = new DatabaseHelper(provider);
    String query = "Update sessions set expirationTime=? where id=?";

    databaseHelper.executeQuery(query,session.expirationTime, session.sessionId);
  }
}
