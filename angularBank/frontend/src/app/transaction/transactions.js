/**
 * @author Stanislava Kaukova(sisiivanovva@gmail.com)
 */
angular.module('bank.transactions', [
  'ui.router',
  'angular-growl',
  'bank.http',
  'bank.endpoints'
])

  .config(function ($stateProvider) {
    $stateProvider.state('transactions', {
      url: '/transaction',
      views: {
        "main": {
          controller: 'TransactionCtrl',
          controllerAs: 'vm',
          templateUrl: 'transaction/transactions.tpl.html'
        }
      },
      data: {pageTitle: 'Transactions'}
    });
  })

  .service('accountGateway', function (httpRequest, bankEndpoints) {
    return {
      deposit: function (amount) {
        return httpRequest.post(bankEndpoints.DEPOSIT, {amount: amount});
      },

      withdraw: function (amount) {
        return httpRequest.post(bankEndpoints.WITHDRAW, {amount: amount});
      },

      loadAccountData: function () {
        return httpRequest.get(bankEndpoints.USER_BALANCE);
      }
    };
  })

  .controller('TransactionCtrl', function (accountGateway, growl) {
    var vm = this;

    vm.deposit = function () {
      accountGateway.deposit(vm.amount)
        .then(function (response) {
            vm.balance = response.balance;

            growl.success(response.message);
          },

          function error(response) {
            growl.error(response.message);
          }
        );
    };

    vm.withdraw = function () {
      accountGateway.withdraw(vm.amount)
        .then(function (response) {
            vm.balance = response.balance;

            growl.success(response.message);
          },

          function error(response) {
            growl.error(response.message);
          }
        );
    };

    vm.loadAccountData = function () {
      accountGateway.loadAccountData()
        .then(function (userAccount) {
            vm.email = userAccount.email;
            vm.balance = userAccount.balance;
          },
          function (error) {
            growl.error(error);
          });
    };
  });