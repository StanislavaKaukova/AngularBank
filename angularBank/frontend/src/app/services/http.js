/**
 * @author Stanislava Kaukova(sisiivanovva@gmail.com)
 */
angular.module('bank.http', ['ui.router'])

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