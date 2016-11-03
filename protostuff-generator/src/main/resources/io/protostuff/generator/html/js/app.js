var app = angular.module('app', [
    'controllers',
    'filters',
    'factories',
    'typeRefDirective',
    'ngAnimate',
    'ngRoute',
    'angularBootstrapNavTree',
    'ngMaterial',
    'ngSanitize'
]);

app.config(['$httpProvider', function ($httpProvider) {
    // Enable http caching
    $httpProvider.defaults.cache = true;
}]);

app.config(['$routeProvider', function ($routeProvider) {
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

    $routeProvider.when('/types', {
        templateUrl: 'partials/type-list.html',
        controller: 'TypeListCtrl as ctrl'
    });
    $routeProvider.when('/types/:typeId', {
        templateUrl: 'partials/type-detail.html',
        controller: 'TypeDetailCtrl as ctrl'
    });
    $routeProvider.when('/protos/:protoId', {
        templateUrl: 'partials/proto-detail.html',
        controller: 'ProtoDetailCtrl as ctrl'
    });
    $routeProvider.when('/pages/:pageId', {
        templateUrl: 'partials/page.html',
        controller: 'PageCtrl as ctrl'
    });
    $routeProvider.when('/search/:searchText', {
        templateUrl: 'partials/search-result.html',
        controller: 'SearchResultCtrl as ctrl'
    });
    $routeProvider.otherwise({redirectTo: '/'});
}]);