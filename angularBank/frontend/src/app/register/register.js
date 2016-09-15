/**
 * @author Stanislava Kaukova(sisiivanovva@gmail.com)
 */

angular.module('bank.register', [
  'ui.router',
  'angular-growl',
  'bank.http',
  'bank.endpoints'
])

  .config(function ($stateProvider) {
    $stateProvider.state('register', {
      url: '/register',
      views: {
        "main": {
          controller: 'RegisterCtrl',
          controllerAs: 'vm',
          templateUrl: 'register/register.tpl.html'
        }
      },
      data: {pageTitle: 'Register'}
    });
  })

  .service('userGateway', function (httpRequest, bankEndpoints) {
    return {
      register: function (user, confirmPassword) {
        return httpRequest.post(bankEndpoints.REGISTER, {user: user, confirmPassword: confirmPassword});
      }
    };
  })

  .controller('RegisterCtrl', function (userGateway, growl, $state) {
    var vm = this;

    vm.register = function (user, confirmPassword) {
      userGateway.register(user, confirmPassword)
        .then(function (response) {
            growl.success(response);

            $state.go('login');
          },
          function (errorMsg) {
            growl.error(errorMsg);
          });
    };
  });
