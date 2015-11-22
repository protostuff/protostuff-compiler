var app = angular.module('app', [
    'controllers',
    'filters',
    'typeRefDirective',
    'ngAnimate',
    'ngRoute',
    'angularBootstrapNavTree'
]);

app.config(['$httpProvider', function ($httpProvider) {
    // enable http caching
    $httpProvider.defaults.cache = true;
}]);

app.config(['$routeProvider', function ($routeProvider) {
    $routeProvider.when('/types', {
        templateUrl: 'partials/type-list.html',
        controller: 'TypeListCtrl'
    });

    var scalars = [
        'double',
        'float',
        'int32',
        'int64',
        'uint32',
        'uint64',
        'sint32',
        'sint64',
        'fixed32',
        'fixed64',
        'sfixed32',
        'sfixed64',
        'bool',
        'string',
        'bytes'
    ];

    scalars.forEach(function (value) {
        $routeProvider.when('/types/' + value, {
            templateUrl: 'partials/scalar-value-types.html'
        });
    });

    $routeProvider.when('/types/:typeId', {
        templateUrl: 'partials/type-detail.html',
        controller: 'TypeDetailCtrl'
    });
    $routeProvider.otherwise({redirectTo: '/'});
}]);