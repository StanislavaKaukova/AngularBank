/**
 * @author Stanislava Kaukova(sisiivanovva@gmail.com)
 */
describe('TransactionCtrl tests', function () {
  var ctrl, scope, gateway, deferred, growl, response;

  beforeEach(function () {
    module('bank.transaction');

    inject(function ($controller, $q, $rootScope) {
      scope = $rootScope.$new();
      deferred = $q.defer();
      gateway = {};
      growl = {};

      ctrl = $controller("TransactionCtrl", {
        accountGateway: gateway,
        growl: growl
      });
    });


    it('should deposit in account', function () {
      ctrl.balance = 0.00;

      var response = {
        message: 'success',
        balance: 200
      };

      gateway.makeTransaction = jasmine.createSpy('deposit').and.returnValue(deferred.promise);
      growl.success = jasmine.createSpy('success');

      ctrl.deposit();

      expect(gateway.makeTransaction).toHaveBeenCalled();

      deferred.resolve(response);
      scope.$digest();

      expect(growl.success).toHaveBeenCalledWith(response.message);
      expect(ctrl.balance).toEqual(200);
    });

    it('message for error by deposit', function () {
      ctrl.balance = 0.00;

      response = {
        message: 'error',
        balance: 100
      };

      gateway.makeTransaction = jasmine.createSpy('deposit').and.returnValue(deferred.promise);
      growl.error = jasmine.createSpy('error');

      ctrl.deposit();
      expect(gateway.makeTransaction).toHaveBeenCalled();

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

    it('should withdraw from account', function () {
      ctrl.balance = 200.00;

      var response = {
        message: 'success',
        balance: 100
      };

      gateway.makeTransaction = jasmine.createSpy('withdraw').and.returnValue(deferred.promise);
      growl.success = jasmine.createSpy('success');

      ctrl.withdraw(100);

      expect(gateway.makeTransaction).toHaveBeenCalled();

      deferred.resolve(response);
      scope.$digest();

      expect(growl.success).toHaveBeenCalledWith(response.message);
      expect(ctrl.balance).toEqual(100);
    });

    it('message for error by withdraw', function () {
      ctrl.balance = 0.00;

      response = {
        message: 'error',
        balance: 0.00
      };

      gateway.makeTransaction = jasmine.createSpy('withdraw').and.returnValue(deferred.promise);
      growl.error = jasmine.createSpy('error');

      ctrl.withdraw('as');
      expect(gateway.makeTransaction).toHaveBeenCalled();

      deferred.reject(response);
      scope.$digest();

      expect(growl.error).toHaveBeenCalledWith(response.message);
      expect(ctrl.balance).toEqual(0.00);
    });

    it('message for error by withdraw from insufficient balance', function () {
      ctrl.balance = 0.00;

      response = {
        message: 'error',
        balance: 0.00
      };

      gateway.makeTransaction = jasmine.createSpy('withdraw').and.returnValue(deferred.promise);
      growl.error = jasmine.createSpy('error');

      ctrl.withdraw(100);
      expect(gateway.makeTransaction).toHaveBeenCalled();

      deferred.reject(response);
      scope.$digest();

      expect(growl.error).toHaveBeenCalledWith(response.message);
      expect(ctrl.balance).toEqual(0.00);
    });
  });
});