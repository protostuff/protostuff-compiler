var controllers = angular.module('controllers', []);

function TreeCtrl($scope, $http, $location) {
    $scope.tree = {};
    $scope.tree_data = [];

    $scope.init = function() {
        $http.get('data/index.json')
            .then(function (res) {
                $scope.tree_data = res.data;
            });
    };

    $scope.show = function (row) {
        $location.path('/types/' + row.data.ref);
    };
}
controllers.controller('TreeCtrl', ['$scope', '$http', '$location', TreeCtrl]);

function TypeListCtrl($scope, $http) {

}
controllers.controller('TypeListCtrl', ['$scope', '$http', TypeListCtrl]);

function TypeDetailCtrl($scope, $http, $routeParams) {
    var typeId;
    $scope.typeId = typeId = $routeParams.typeId;
    $http.get('data/' + typeId + '.json')
        .success(function (data) {
            $scope.type = data;
        });
}
controllers.controller('TypeDetailCtrl', ['$scope', '$http', '$routeParams', TypeDetailCtrl]);