/**
 * @author Stanislava Kaukova(sisiivanovva@gmail.com)
 */
angular.module('bank.deposit', [
  'ui.router',
  'angular-growl',
  'bank.http',
  'bank.endpoints'
])

  .config(function ($stateProvider) {
    $stateProvider.state('deposit', {
      url: '/deposit',
      views: {
        "main": {
          controller: 'DepositController',
          controllerAs: 'vm',
          templateUrl: 'deposit/deposit.tpl.html'
        }
      },
      data: {pageTitle: 'Deposit'}
    });
  })

  .service('accountGateway', function (httpRequest, bankEndpoints) {
    return {
      deposit: function (amount) {
        return httpRequest.post(bankEndpoints.TRANSACTIONS, {amount: amount});
      },

      loadAccountData: function () {
        return httpRequest.get(bankEndpoints.ACCOUNT);
      }
    };
  })

  .controller('DepositController', function (accountGateway, growl) {
    var vm = this;

    vm.deposit = function () {
      accountGateway.deposit(vm.amount)
        .then(function (response) {
            vm.balance = response.balance;

            growl.success(response.message);
          },

          function error(response) {
            growl.error(response.message);
          });
    };

    vm.loadAccountData = function () {
      accountGateway.loadAccountData()
        .then(function (userAccount) {
          vm.email = userAccount.email;
          vm.balance = userAccount.balance;
        });
    };
  });