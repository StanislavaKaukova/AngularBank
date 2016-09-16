package com.clouway.bank.core;

/**
 * @author Stanislava Kaukova(sisiivanovva@gmail.com)
 */

import com.google.common.base.Optional;

/**
 * The implementation of this interface will be used to createSession and retrieve session data
 *
 * @author Stanislava Kaukova(sisiivanovva@gmail.com)
 */
public interface SessionRepository {
  /**
   * Will save session information
   *
   * @param session session
   */
  void createSession(Session session);

  /**
   * find session by email to user
   *
   * @param id session id
   * @return session
   */
  Optional<Session> findSessionById(String id);

  /**
   * Will remove session
   *
   * @param id session id
   */
  void remove(String id);

  /**
   * Will find current email
   *
   * @param sessionId session id
   * @return current user email
   */
  String findUserEmailBySid(String sessionId);

  void update(Session session);

  /**
   * Will return online users
   *
   * @return online users
   */
  int getOnlineUsersCount();
}

