/**
 * @author Stanislava Kaukova(sisiivanovva@gmail.com)
 */
angular.module('bank', [
  'templates-app',
  'templates-common',
  'ui.router',
  'angular-growl',
  'bank.deposit'
])

  .config(function myAppConfig($stateProvider, $urlRouterProvider) {
    $urlRouterProvider.otherwise('/home');
  })

  .controller('BankController', function ($scope) {
    $scope.$on('$stateChangeSuccess', function (event, toState, toParams, fromState, fromParams) {
      if (angular.isDefined(toState.data.pageTitle)) {
        $scope.pageTitle = toState.data.pageTitle + ' | bank';
      }
    });
  });
