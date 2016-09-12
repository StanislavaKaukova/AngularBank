package com.clouway.bank.guiceModule;

import com.clouway.bank.adapter.http.CurrentUserService;
import com.clouway.bank.adapter.http.UserAccountService;
import com.clouway.bank.adapter.http.DepositService;
import com.clouway.bank.adapter.http.WithdrawService;
import com.clouway.bank.adapter.http.LoginService;
import com.clouway.bank.adapter.http.SecurityFilter;
import com.google.inject.servlet.ServletModule;

/**
 * @author Stanislava Kaukova(sisiivanovva@gmail.com)
 */
public class BankServletModule extends ServletModule {
  @Override
  protected void configureServlets() {
    filter("/*").through(SecurityFilter.class);

    serve("/r/account/deposit").with(DepositService.class);
    serve("/r/user/account/balance").with(UserAccountService.class);
    serve("/r/account/withdraw").with(WithdrawService.class);
    serve("/r/login").with(LoginService.class);
    serve("/r/account/currentAccount").with(CurrentUserService.class);
  }
}
