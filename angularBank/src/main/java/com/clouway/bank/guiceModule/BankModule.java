package com.clouway.bank.guiceModule;

import com.clouway.bank.adapter.jdbc.persistence.JdbcAdapterModule;
import com.clouway.bank.core.CurrentTime;
import com.clouway.bank.core.CurrentTimeImplementation;
import com.clouway.bank.core.IdGenerator;
import com.clouway.bank.core.User;
import com.clouway.bank.core.Validator;
import com.clouway.bank.utils.SessionIdFinder;
import com.clouway.bank.utils.SessionIdGenerator;
import com.clouway.bank.validator.AmountValidator;
import com.clouway.bank.validator.UserValidator;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.name.Names;

/**
 * @author Stanislava Kaukova(sisiivanovva@gmail.com)
 */
public class BankModule extends AbstractModule {
  @Override
  protected void configure() {
    install(new JdbcAdapterModule());

    bind(IdGenerator.class).to(SessionIdGenerator.class);

    bind(Validator.class)
            .annotatedWith(Names.named("amountValidator"))
            .to(AmountValidator.class);
  }

  @Provides
  Validator<User> getUserValidator() {
    return new UserValidator();
  }

  @Provides
  SessionIdFinder getSessionId() {
    return new SessionIdFinder("sessionId");
  }

  @Provides
  CurrentTime getCurrentTime() {
    return new CurrentTimeImplementation(10000);
  }
}