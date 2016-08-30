/**
 * @author Stanislava Kaukova(sisiivanovva@gmail.com)
 */
describe('BankController', function () {
  describe('Bank Controller Test', function () {
    var BankController, $scope;

    beforeEach(function () {
      module('bank');

      inject(function ($controller, $rootScope) {
        $scope = $rootScope.$new();
        BankController = $controller('BankController', {$scope: $scope});
      });
    });

    it('Bank controller', inject(function () {
      expect(BankController).toBeTruthy();
    }));
  });
});