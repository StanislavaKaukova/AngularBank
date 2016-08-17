/**
 * @author Stanislava Kaukova(sisiivanovva@gmail.com)
 */
describe("LoginCtrl test", function () {
  var ctrl, scope, gateway, deferred, growl, cookies, state;

  beforeEach(function () {
    module('bank.login');


    inject(function ($controller, $q, $rootScope, $cookies,$state) {
      scope = $rootScope.$new();
      deferred = $q.defer();
      gateway = {};
      growl = {};
      cookies = $cookies;
      state = $state;

      ctrl = $controller('LoginCtrl', {
        loginGateway: gateway,
        growl: growl,
        cookies: cookies,
        state: state
      });
    });
  });

  it("should login and set cookie", function () {
    var response = {
      message: "success",
      sessionId: 'test'
    };

    var currentTime = getCurrentTime();

    var expireTime = {
      expires: currentTime
    };

    gateway.login = jasmine.createSpy('login').and.returnValue(deferred.promise);
    cookies.put = jasmine.createSpy('put cookies');
    growl.success = jasmine.createSpy('success');
    state.go = jasmine.createSpy('state go');

    ctrl.login('test@test.com', '123456789');

    expect(gateway.login).toHaveBeenCalled();

    deferred.resolve(response);
    scope.$digest();

    expect(cookies.put).toHaveBeenCalledWith('sessionId', response.sessionId, expireTime);
    expect(growl.success).toHaveBeenCalledWith(response.message);
    expect(state.go).toHaveBeenCalledWith("transactions");
  });

  it('should return error by login', function () {
    var errorMessage = "Error";

    gateway.login = jasmine.createSpy('login').and.returnValue(deferred.promise);
    growl.error = jasmine.createSpy('error');

    ctrl.login('test@test.com', '123456789');
    expect(gateway.login).toHaveBeenCalled();

    deferred.reject(errorMessage);
    scope.$digest();

    expect(growl.error).toHaveBeenCalledWith(errorMessage);
  });

  function getCurrentTime() {
    jasmine.clock().install();

    var baseTime = new Date();
    jasmine.clock().mockDate(baseTime);

    var expireTime = baseTime.setMinutes(baseTime.getMinutes() + 100);

    return new Date(expireTime);
  }
});
