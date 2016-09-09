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
      makeTransaction: function (amount, type) {
        return httpRequest.post(bankEndpoints.TRANSACTIONS + type, {amount: amount});
      },

      loadAccountData: function () {
        return httpRequest.get(bankEndpoints.ACCOUNT);
      }
    };
  })

  .controller('TransactionCtrl', function (accountGateway, growl) {
    var vm = this;

    vm.deposit = function () {
      makeTransaction('deposit');
    };

    vm.withdraw = function () {
      makeTransaction('withdraw');
    };

    vm.loadAccountData = function () {
      accountGateway.loadAccountData()
        .then(function (userAccount) {
          vm.email = userAccount.email;
          vm.balance = userAccount.balance;
        });
    };

    function makeTransaction(type) {
      accountGateway.makeTransaction(vm.amount, type)
        .then(function (response) {
            vm.balance = response.balance;

            growl.success(response.message);
          },

          function error(response) {
            growl.error(response.message);
          }
        );
    }
  });