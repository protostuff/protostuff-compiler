angular.module('typeRefDirective', [])
    .directive('typeRef', function () {
        return {
            restrict: 'E',
            scope: {
                value: '='
            },
            templateUrl: 'partials/type-ref.html'
        };
    });
