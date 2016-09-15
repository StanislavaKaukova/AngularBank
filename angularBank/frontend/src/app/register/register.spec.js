/**
 * @author Stanislava Kaukova(sisiivanovva@gmail.com)
 */
describe('RegisterCtrl', function () {
  var ctrl, scope, gateway, deferred, growl, state;

  beforeEach(function () {
    module('bank.register');

    inject(function ($controller, $q, $rootScope, $state) {
      scope = $rootScope.$new();
      deferred = $q.defer();
      gateway = {};
      growl = {};
      state = $state;

      ctrl = $controller("RegisterCtrl", {
        userGateway: gateway,
        growl: growl
      });
    });
  });

  it('should register user', function () {
    var user = {
      name: 'Ivan'
    };

    var confirmPassword = {
      password: 'confirmPassword'
    };

    var response = 'success';

    gateway.register = jasmine.createSpy('register').and.returnValue(deferred.promise);
    growl.success = jasmine.createSpy('success');
    state.go = jasmine.createSpy('state go');

    ctrl.register(user, confirmPassword);

    expect(gateway.register).toHaveBeenCalledWith(user, confirmPassword);

    deferred.resolve(response);
    scope.$digest();

    expect(growl.success).toHaveBeenCalledWith(response);
    expect(state.go).toHaveBeenCalledWith("login");
  });

  it('should return error by register', function () {
    var user = {
      name: 'Lilia'
    };
    var confirmPassword = {
      password: 'confirmPassword'
    };

    var response = 'error';

    gateway.register = jasmine.createSpy('register').and.returnValue(deferred.promise);
    growl.error = jasmine.createSpy('error');

    ctrl.register(user, confirmPassword);

    expect(gateway.register).toHaveBeenCalledWith(user, confirmPassword);

    deferred.reject(response);
    scope.$digest();

    expect(growl.error).toHaveBeenCalledWith(response);
  });
});
