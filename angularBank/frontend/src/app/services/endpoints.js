/**
 * Created by clouway on 05.09.16.
 */
angular.module('bank.endpoints', [])
  .constant('bankEndpoints', {
    LOGIN: '/r/login',
    USER_ACCOUNT: '/r/account/currentAccount',
    DEPOSIT: '/r/account/deposit',
    WITHDRAW: '/r/account/withdraw',
    USER_BALANCE: '/r/user/account/balance',
    TRANSACTIONS: '/r/account/',
    ACCOUNT_HISTORY: '/r/account/history',
    ACCOUNT_HISTORY_PAGES: '/r/transactionsHistory/totalPages'
    USER_BALANCE: '/r/user/account/balance',
    REGISTER: '/r/register'
  });
