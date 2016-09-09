/**
 * @author Stanislava Kaukova(sisiivanovva@gmail.com)
 */
describe('httpRequest service test', function () {
  var httpRequest = {};
  var httpBackend;

  beforeEach(function () {
    module('bank.http');

    inject(function (_httpRequest_, $httpBackend) {
      httpRequest = _httpRequest_;
      httpBackend = $httpBackend;
    });
  });

  it('should make get request', function () {
    httpBackend.when('GET', '/user').respond('ivan');

    var returnedPromise = httpRequest.get('/user');
    returnedPromise.then(function (response) {
      expect(response).toBe('ivan');
    });

    httpBackend.flush();
  });

  it('should make post request', function () {
    httpBackend.when('POST', '/user').respond('krasimir');

    var returnedPromise = httpRequest.post('/user');
    returnedPromise.then(function (response) {
      expect(response).toBe('krasimir');
    });
    httpBackend.flush();
  });


  it('should make get request with params', function () {
    httpBackend.when('GET', '/user?id=1').respond('ivailo');

    var returnedPromise = httpRequest.get('/user?id=1');
    returnedPromise.then(function (response) {
      expect(response).toBe('ivailo');
    });
    httpBackend.flush();
  });

  it('should make get request with error 404', function () {
    httpBackend.when('GET', '/transactions').respond(404, 'error');

    var returnedPromise = httpRequest.get('/transactions');
    returnedPromise.then(function success(response) {

    }, function error(response) {
      expect(response).toBe('error');
    });
    httpBackend.flush();
  });
});
