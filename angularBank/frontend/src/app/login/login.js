/**
 * @author Stanislava Kaukova(sisiivanovva@gmail.com)
 */
angular.module('bank.login', [
  'angular-growl',
  'ui.router',
  'bank.http',
  'bank.endpoints',
  'ngCookies'
])

  .config(function ($stateProvider) {
    $stateProvider.state('login', {
      url: '/login',
      views: {
        "main": {
          controller: 'LoginCtrl',
          controllerAs: 'vm',
          templateUrl: 'login/login.tpl.html'
        }
      },
      data: {pageTitle: 'Login'}
    });
  })

  .service('loginGateway', function (httpRequest, bankEndpoints) {
    return {
      login: function (email, password) {
        return httpRequest.post(bankEndpoints.LOGIN, {email: email, password: password});
      }
    };
  })

  .controller('LoginCtrl', function (loginGateway, growl, $cookies, $rootScope, $state) {
    var vm = this;
    vm.login = function (email,password) {
      loginGateway.login(email, password)
        .then(function (response) {
            $rootScope.currentAccount = vm.email;

            var expireTime = new Date();
            expireTime.setMinutes(expireTime.getMinutes() + 10);

            var sessionId = response.sessionId;

            $cookies.put('sessionId', sessionId, {expires: expireTime});

            growl.success(response.message);

            $state.go('transactions');
          },

          function (response) {
            growl.error(response);
          });
    };
  });
