angular.module('typeRefDirective', [])
    .directive('typeRef', function () {
        return {
            restrict: 'E',
            scope: {
                value: '='
            },
            templateUrl: 'partials/type-ref.html'
        };
    })
    .directive('labelDeprecated', function () {
        return {
            restrict: 'E',
            scope: {
                descriptor: '='
            },
            templateUrl: 'partials/label-deprecated.html'
        };
    });
