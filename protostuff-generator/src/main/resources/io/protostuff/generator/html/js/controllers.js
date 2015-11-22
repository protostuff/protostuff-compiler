var controllers = angular.module('controllers', []);

function TreeCtrl($scope, $http, $location) {
    $scope.tree = {};
    $scope.tree_data = [];

    console.log("TreeCtrl start");

    $scope.init = function() {
        console.log("started init");
        //$scope.doing_async = true;
        $http.get('data/index.json')
            .then(function (res) {
                $scope.tree_data = res.data;
                //$scope.doing_async = false;
                $scope.tree.expand_all();
                console.log("finished data loading");
            });
        console.log("finished init");
    };

    $scope.show = function (row) {
        $location.path('/types/' + row.data.ref);
        console.log(row.label);
        $scope.tree.expand_all();
    };
    console.log("TreeCtrl finish");
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