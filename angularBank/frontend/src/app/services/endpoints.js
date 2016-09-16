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
    USER_ONLINE: 'r/account/onlineUsers'
  });
