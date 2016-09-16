/**
 * Created by Stanislava on 9/16/2016.
 */
angular.module('bank.logout', [
  'ui.router',
  'bank.http',
  'bank.endpoints',
  'ngCookies'
])

        .config(function ($stateProvider) {
          $stateProvider.state('logout', {
            url: '/logout',
            views: {
              "main": {
                controller: 'LogoutCtrl',
                controllerAs: 'vm',
                templateUrl: ''
              }
            },
            data: {pageTitle: 'Logout'}
          });
        })


        .service('logoutGateway', function (httpRequest, bankEndpoints) {
          return {
            logout: function () {
              return httpRequest.get(bankEndpoints.LOGOUT);
            }
          };
        })
        
        .controller('LogoutCtrl', function (logoutGateway, $cookies, growl, $state) {
          var vm = this;
          console.log("logout");
          vm.logout = function () {
            logoutGateway.logout()
                    .then(function (sessionId) {
                      $cookies.remove(sessionId);
                      growl.success("Success logout");
                      $state.go('login');
                    }, function (errorMsg) {
                      growl.error(errorMsg);
                    });
          };
        });