/**
 * Created by clouway on 05.09.16.
 */
angular.module('bank.endpoints', [])
  .constant('bankEndpoints', {
    TRANSACTIONS: '/r/account/',
    ACCOUNT: '/r/account/balance'
  });
