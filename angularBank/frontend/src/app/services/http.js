/**
 * @author Stanislava Kaukova(sisiivanovva@gmail.com)
 */
function requestInterceptor($q, $injector) {
  return {
    response: function (res) {
      return $q.resolve(res);
    },

    responseError: function (res) {
      if (res.status === 401) {
        var $state = $injector.get('$state');
        $state.go("login");
      }
      return $q.reject(res);
    }
  };
}

angular.module('bank.http', ['ui.router'])
  .factory('requestInterceptor', requestInterceptor)

  .config(function ($httpProvider) {
    $httpProvider.interceptors.push('requestInterceptor');
  })

  .service('httpRequest', function ($http, $q) {

    this.get = function (url, params, data) {
      return this.send('GET', url, data, params);
    };

    this.post = function (url, data, params) {
      return this.send('POST', url, data, params);
    };

    this.send = function (method, url, data, params) {
      var deferred = $q.defer();

      $http({method: method, url: url, data: data, params: params})
        .success(function (data) {
          deferred.resolve(data);
        })

        .error(function (data, status) {

          if (status === 404 && !data) {
            deferred.resolve();
          } else {
            deferred.reject(data);
          }
        });

      return deferred.promise;
    };
  });
