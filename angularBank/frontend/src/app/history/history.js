/**
 * Created by clouway on 14.09.16.
 */
angular.module('bank.history', [
  'ui.router',
  'angular-growl',
  'bank.http',
  'bank.endpoints'
])
  .config(function ($stateProvider) {
    $stateProvider.state('transactionHistory', {
      url: '/history',
      views: {
        "main": {
          controller: 'TransactionHistoryCtrl',
          controllerAs: 'vm',
          templateUrl: 'history/history.tpl.html'
        }
      },
      data: {pageTitle: 'History'}
    });
  })

  .service('historyGateway', function (httpRequest, bankEndpoints) {
    return {
      loadPage: function (pageNumber) {
        return httpRequest.get(bankEndpoints.ACCOUNT_HISTORY, {pageNumber: pageNumber}
        );
      },
      getTotalPages: function () {
        return httpRequest.get(bankEndpoints.ACCOUNT_HISTORY_PAGES);
      }
    };
  })
  .controller('TransactionHistoryCtrl', function (historyGateway,growl) {
    var vm = this;
    vm.currentPage = 1;

    vm.loadPage = function (currentPage) {
      historyGateway.loadPage(currentPage)
        .then(function (response) {
            vm.transactions = response;
            this.currentPage = currentPage;
          },
          function () {
            growl.error('Can not load page!');
          });
    };

    vm.getPrevious = function () {
      if (vm.currentPage == 1) {
        vm.loadPage(1);
      } else {
        vm.currentPage--;
        vm.loadPage(vm.currentPage);
      }
    };

    vm.getNext = function () {
      var totalPageNumbers = vm.pages;
      var lastPageNumber = totalPageNumbers[totalPageNumbers.length - 1];

      if (lastPageNumber <= vm.currentPage) {
        vm.loadPage(lastPageNumber);
      } else {
        vm.currentPage++;
        vm.loadPage(vm.currentPage);
      }
    };

    vm.getTotalPages = function () {
      historyGateway.getTotalPages()
        .then(function (response) {
            vm.pages = response;
          },
          function () {
            growl.error('Can not load total number of pages');
          });
    };
  });