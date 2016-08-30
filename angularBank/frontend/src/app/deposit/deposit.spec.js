/**
 * @author Stanislava Kaukova(sisiivanovva@gmail.com)
 */
describe('DepositController tests', function () {
  var ctrl, scope, gateway, deferred, growl;

  beforeEach(module('bank.deposit'));

  beforeEach(inject(function ($controller, $q, $rootScope) {
    scope = $rootScope.$new();
    deferred = $q.defer();
    gateway = {};
    growl = {};

    ctrl = $controller("DepositController", {
      accountGateway: gateway,
      growl: growl
    });
  }));

  it('should deposit in account', function () {
    ctrl.balance = 0.00;

    var response = {
      message: 'success',
      balance: 200
    };
    
    gateway.deposit = jasmine.createSpy('deposit').and.returnValue(deferred.promise);
    growl.success = jasmine.createSpy('success');

    ctrl.deposit();

    expect(gateway.deposit).toHaveBeenCalled();

    deferred.resolve(response);
    scope.$digest();

    expect(growl.success).toHaveBeenCalledWith(response.message);
    expect(ctrl.balance).toEqual(200);
  });

  it('message for error by deposit', function () {
    ctrl.balance = 0.00;

    var response = {
      message: 'error',
      balance: 100
    };

    gateway.deposit = jasmine.createSpy('deposit').and.returnValue(deferred.promise);
    growl.error = jasmine.createSpy('error');

    ctrl.deposit();
    expect(gateway.deposit).toHaveBeenCalled();

    deferred.reject(response);
    scope.$digest();

    expect(growl.error).toHaveBeenCalledWith(response.message);
    expect(ctrl.balance).toEqual(0.00);
  });

  it('should load account data', function () {
    var account = {
      balance: 100,
      email: 'l@avc.xs'
    };

    gateway.loadAccountData = jasmine.createSpy('load account data').and.returnValue(deferred.promise);

    ctrl.loadAccountData();
    
    expect(gateway.loadAccountData).toHaveBeenCalled();
    
    deferred.resolve(account);
    scope.$digest();

    expect(ctrl.balance).toEqual(account.balance);
    expect(ctrl.email).toEqual(account.email);
  });
});