/**
 * Created by clouway on 16.09.16.
 */
angular.module('bank.home', [
  'ui.router',
  'angular-growl',
  'bank.http',
  'bank.endpoints'
])
  .config(function ($stateProvider) {
    $stateProvider.state('home', {
      url: '/home',
      views: {
        "main": {
          controller: 'HomeCtrl',
          controllerAs: 'vm',
          templateUrl: 'home/home.tpl.html'
        }
      },
      data: {pageTitle: 'Home'}
    });
  })

  .service('homeGateway', function (httpRequest, bankEndpoints) {
    return {
      getOnlineUsers: function () {
        return httpRequest.get(bankEndpoints.USER_ONLINE);
      }
    };
  })

  .controller('HomeCtrl', function (homeGateway,growl) {
    var vm = this;

    vm.getOnlineUsers = function () {
      homeGateway.getOnlineUsers()
        .then(function (response) {
            vm.onlineUsers = response;
          },
          function (response) {
            growl.error(response);
          });
    };
  });