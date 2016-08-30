package com.clouway.bank.guiceModule;

import com.clouway.bank.adapter.http.UserAccountService;
import com.clouway.bank.adapter.http.DepositService;
import com.google.inject.servlet.ServletModule;

/**
 * @author Stanislava Kaukova(sisiivanovva@gmail.com)
 */
public class BankServletModule extends ServletModule {
  @Override
  protected void configureServlets() {
    serve("/r/account/deposit").with(DepositService.class);
    serve("/r/account/balance").with(UserAccountService.class);
  }
}
