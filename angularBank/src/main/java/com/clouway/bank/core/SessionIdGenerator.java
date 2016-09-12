package com.clouway.bank.core;

import java.util.UUID;

/**
 * @author Stanislava Kaukova(sisiivanovva@gmail.com)
 */
public class SessionIdGenerator implements IdGenerator {
  @Override
  public String generateId() {
    return UUID.randomUUID().toString();
  }
}
