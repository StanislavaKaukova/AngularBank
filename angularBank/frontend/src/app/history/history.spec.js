/**
 * @author Stanislava Kaukova(sisiivanovva@gmail.com)
 */
describe("Transaction History test", function () {
  var ctrl, scope, gateway, deferred, growl;

  beforeEach(function () {
    module('bank.history');

    inject(function ($controller, $q, $rootScope) {
      scope = $rootScope.$new();
      deferred = $q.defer();
      gateway = {};
      growl = {};

      ctrl = $controller('TransactionHistoryCtrl', {
        historyGateway: gateway,
        growl: growl
      });
    });
  });

  it('should get load table with transaction history', function () {
    var response = [
      {date: '12.12.2012', operation: 'deposit'},
      {date: '13.12.2012', operation: 'withdraw'},
      {date: '14.12.2012', operation: 'deposit'},
      {date: '12.12.2012', operation: 'deposit'}];

    gateway.loadPage = jasmine.createSpy('load page').and.returnValue(deferred.promise);

    ctrl.loadPage();

    expect(gateway.loadPage).toHaveBeenCalled();
    deferred.resolve(response);
    scope.$digest();

    expect(ctrl.transactions).toEqual(response);
    expect(ctrl.currentPage).toEqual(1);
  });

  it('should return error by load page', function () {
    var errorMessage = 'Can not load page!';

    gateway.loadPage = jasmine.createSpy('load page').and.returnValue(deferred.promise);
    growl.error = jasmine.createSpy('error');

    ctrl.loadPage();

    expect(gateway.loadPage).toHaveBeenCalled();

    deferred.reject(errorMessage);
    scope.$digest();

    expect(growl.error).toHaveBeenCalledWith(errorMessage);
  });

  it('should load next page', function () {
    var response = [
      {date: '13.12.2012', operation: 'withdraw'},
      {date: '14.12.2012', operation: 'deposit'}
    ];

    ctrl.pages = [1, 2];
    ctrl.currentPage = 1;

    gateway.loadPage = jasmine.createSpy('load page').and.returnValue(deferred.promise);
    gateway.getTotalPages = jasmine.createSpy('get total pages').and.returnValue(deferred.promise);

    ctrl.getTotalPages();
    ctrl.getNext();
    ctrl.loadPage(2);

    expect(gateway.loadPage).toHaveBeenCalled();
    expect(gateway.getTotalPages).toHaveBeenCalled();

    deferred.resolve(response);
    scope.$digest();

    expect(ctrl.currentPage).toEqual(2);
  });

  it('should return last page if not have another pages', function () {
    var response = [
      {date: '13.12.2012', operation: 'withdraw'},
      {date: '14.12.2012', operation: 'deposit'}
    ];

    ctrl.pages = [1, 2];
    ctrl.currentPage = 2;

    gateway.loadPage = jasmine.createSpy('load page').and.returnValue(deferred.promise);
    gateway.getTotalPages = jasmine.createSpy('get total pages').and.returnValue(deferred.promise);

    ctrl.getTotalPages();
    ctrl.getNext();
    ctrl.loadPage();

    expect(gateway.loadPage).toHaveBeenCalled();
    expect(gateway.getTotalPages).toHaveBeenCalled();

    deferred.resolve(response);
    scope.$digest();

    expect(ctrl.currentPage).toEqual(2);
  });

  it('should load previous page', function () {
    var response = [
      {date: '13.12.2012', operation: 'withdraw'},
      {date: '14.12.2012', operation: 'deposit'}
    ];

    ctrl.pages = [1, 2];
    ctrl.currentPage = 2;

    gateway.loadPage = jasmine.createSpy('load page').and.returnValue(deferred.promise);
    gateway.getTotalPages = jasmine.createSpy('get total pages').and.returnValue(deferred.promise);

    ctrl.getTotalPages();
    ctrl.getPrevious();
    ctrl.loadPage(1);

    expect(gateway.loadPage).toHaveBeenCalled();
    expect(gateway.getTotalPages).toHaveBeenCalled();

    deferred.resolve(response);
    scope.$digest();

    expect(ctrl.currentPage).toEqual(1);
  });

  it('should return first page if not have previous', function () {
    var response = [
      {date: '13.12.2012', operation: 'withdraw'},
      {date: '14.12.2012', operation: 'deposit'}
    ];

    ctrl.pages = [1, 2];
    ctrl.currentPage = 1;

    gateway.loadPage = jasmine.createSpy('load page').and.returnValue(deferred.promise);
    gateway.getTotalPages = jasmine.createSpy('get total pages').and.returnValue(deferred.promise);

    ctrl.getTotalPages();
    ctrl.getPrevious();
    ctrl.loadPage(1);

    expect(gateway.loadPage).toHaveBeenCalled();
    expect(gateway.getTotalPages).toHaveBeenCalled();

    deferred.resolve(response);
    scope.$digest();

    expect(ctrl.currentPage).toEqual(1);
  });
});