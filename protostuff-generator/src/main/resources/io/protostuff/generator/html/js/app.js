var app = angular.module('app', [
    'controllers',
    'ngAnimate',
    'ngRoute',
    'angularBootstrapNavTree'
]);

app.config(['$httpProvider', function ($httpProvider) {
        // enable http caching
        $httpProvider.defaults.cache = true;
    }]);

app.config(['$routeProvider', function ($routeProvider) {
    $routeProvider.
    when('/types', {
        templateUrl: 'partials/type-list.html',
        controller: 'TypeListCtrl'
    }).
    when('/types/:typeId', {
        templateUrl: 'partials/type-detail.html',
        controller: 'TypeDetailCtrl'
    }).
    otherwise({
                  redirectTo: '/'
              });
}]);