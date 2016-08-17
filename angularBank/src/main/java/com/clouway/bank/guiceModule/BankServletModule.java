package com.clouway.bank.guiceModule;

import com.clouway.bank.adapter.http.*;
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
    serve("/r/register").with(RegisterService.class);
    serve("/r/user/login").with(LoginService.class);
    serve("/r/login").with(LoginService.class);
    serve("/r/account/currentAccount").with(CurrentUserService.class);
    serve("/r/account/history").with(TransactionHistoryService.class);
    serve("/r/transactionsHistory/totalPages").with(TotalPagesService.class);
  }
}
