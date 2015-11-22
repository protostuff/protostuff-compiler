angular.module('typeRefDirective', [])
    //.controller('Controller', ['$scope', function($scope) {
    //    $scope.customer = {
    //        name: 'Naomi',
    //        address: '1600 Amphitheatre'
    //    };
    //}])
    .directive('typeRef', function() {
        return {
            restrict: 'E',
            scope: {
                value: '='
            },
            templateUrl: 'partials/type-ref.html'
        };
    });
