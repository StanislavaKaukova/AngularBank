/**
 * @author Stanislava Kaukova(sisiivanovva@gmail.com)
 */
describe('BankController', function () {
  describe('Bank Controller Test', function () {
    var BankController, $scope, growl, accountService, deferred;

    beforeEach(function () {
      module('bank');

      inject(function ($controller, $rootScope, $q) {
        $scope = $rootScope.$new();
        deferred = $q.defer();
        growl = {};

        accountService = {};
        BankController = $controller('BankController', {
          $scope: $scope,
          accountService: accountService,
          growl: growl
        });
      });
    });

    it("load email after success login", function () {
      var response = "test@abv.bg";
      accountService.loadUserAccount = jasmine.createSpy('initial data').and.returnValue(deferred.promise);

      BankController.loadUserAccount();
      expect(accountService.loadUserAccount).toHaveBeenCalled();
    });

    it('error by try load email of current user', function () {
      var errorMessage = "Can not load current user";

      accountService.loadUserAccount = jasmine.createSpy('initial data').and.returnValue(deferred.promise);
      growl.error = jasmine.createSpy('error message');

      BankController.loadUserAccount();
      expect(accountService.loadUserAccount).toHaveBeenCalled();


      deferred.reject(errorMessage);
      $scope.$digest();

      expect(growl.error).toHaveBeenCalledWith(errorMessage);
    });
  });
});