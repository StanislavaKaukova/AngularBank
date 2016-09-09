/**
 * @author Stanislava Kaukova(sisiivanovva@gmail.com)
 */
angular.module('bank', [
  'templates-app',
  'templates-common',
  'ui.router',
  'ngCookies',
  'angular-growl',
  'bank.transactions',
  'bank.login',
  'bank.home'
])

  .config(function myAppConfig($stateProvider, $urlRouterProvider) {
    $urlRouterProvider.otherwise('/home');
  })

  .service('accountService', function (httpRequest, bankEndpoints) {
    return {
      loadUserAccount: function () {
        return httpRequest.get(bankEndpoints.USER_ACCOUNT);
      }
    };
  })

  .controller('BankController', function ($scope, accountService, growl, $rootScope) {
    var vm = this;

    vm.loadUserAccount = function () {
      accountService.loadUserAccount()
        .then(function (account) {
            $rootScope.currentAccount = account;
          },
          function (errorMsg) {
            growl.error(errorMsg);
          });
    };

    $scope.$on('$stateChangeSuccess', function (event, toState, toParams, fromState, fromParams) {
      if (angular.isDefined(toState.data.pageTitle)) {
        $scope.pageTitle = toState.data.pageTitle + ' | bank';
      }
    });
  });
