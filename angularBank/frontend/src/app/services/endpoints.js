/**
 * Created by clouway on 05.09.16.
 */
angular.module('bank.endpoints', [])
  .constant('bankEndpoints', {
    LOGIN: '/r/login',
    USER_ACCOUNT: '/r/account/currentAccount',
    DEPOSIT: '/r/account/deposit',
    WITHDRAW: '/r/account/withdraw',
<<<<<<< HEAD
    USER_BALANCE: '/r/user/account/balance'
=======
    USER_BALANCE: '/r/account/currentBalance',
    USER_ONLINE: 'r/account/onlineUsers'
>>>>>>> angularjs: created home page for the bank project
  });
