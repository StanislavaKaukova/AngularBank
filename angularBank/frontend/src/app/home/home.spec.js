/**
 * Created by clouway on 16.09.16.
 */
describe('HomeCtrl tests', function () {
  var ctrl, scope, growl, gateway, deferred;

  beforeEach(function () {
    module('bank.home');

    inject(function ($controller, $q, $rootScope) {
      scope = $rootScope.$new();
      deferred = $q.defer();
      gateway = {};
      growl = {};

      ctrl = $controller('HomeCtrl', {
        homeGateway: gateway,
        growl: growl
      });
    });
  });

  it('should count online users', function () {
    var response = 1;

    gateway.getOnlineUsers = jasmine.createSpy('get online users').and.returnValue(deferred.promise);

    ctrl.getOnlineUsers();
    expect(gateway.getOnlineUsers).toHaveBeenCalled();

    deferred.resolve(response);
    scope.$digest();

    expect(ctrl.onlineUsers).toEqual(1);
  });

  it('should return error by get online users', function () {
    var response = "Error";

    gateway.getOnlineUsers = jasmine.createSpy('get online users').and.returnValue(deferred.promise);
    growl.error = jasmine.createSpy('error');

    ctrl.getOnlineUsers();
    expect(gateway.getOnlineUsers).toHaveBeenCalled();

    deferred.reject(response);
    scope.$digest();

    expect(growl.error).toHaveBeenCalledWith(response);
  });
});
