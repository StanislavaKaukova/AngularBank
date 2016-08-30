package com.clouway.bank.guiceModule;

import com.clouway.bank.adapter.jdbc.persistence.JdbcAdapterModule;
import com.clouway.bank.core.Validator;
import com.clouway.bank.validator.AmountValidator;
import com.google.inject.AbstractModule;
import com.google.inject.name.Names;

/**
 * @author Stanislava Kaukova(sisiivanovva@gmail.com)
 */
public class BankModule extends AbstractModule {
  @Override
  protected void configure() {
    install(new JdbcAdapterModule());

    bind(Validator.class)
            .annotatedWith(Names.named("amountValidator"))
            .to(AmountValidator.class);
  }
}
