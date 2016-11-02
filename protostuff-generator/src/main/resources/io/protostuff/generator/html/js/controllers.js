var controllers = angular.module('controllers', []);

function TreeCtrl($location, ProtoDataFactory, $q, TreeService) {
    var self = this;
    self.treeService = TreeService;
    self.treeData = [];

    self.show = show;

    loadData().then(function (data) {
        self.treeData = data;
        TreeService.setTreeData(data);
    });

    function loadData() {
        var deferred = $q.defer();
        ProtoDataFactory.get().then(function (data) {
            deferred.resolve(data);
        });
        return deferred.promise;
    }

    function show(row) {
        if (row.data.type === "proto") {
            $location.path('/protos/' + row.data.ref);
        } else {
            $location.path('/types/' + row.data.ref);
        }
    }
}
controllers.controller('TreeCtrl', ['$location', 'ProtoDataFactory', '$q', 'TreeService', TreeCtrl]);

function TypeListCtrl($scope, $http) {

}
controllers.controller('TypeListCtrl', ['$scope', '$http', TypeListCtrl]);

function TypeDetailCtrl($scope, $http, $routeParams) {
    var typeId;
    $scope.typeId = typeId = $routeParams.typeId;
    $http.get('data/type/' + typeId + '.json')
        .success(function (data) {
            $scope.type = data;
        });
}
controllers.controller('TypeDetailCtrl', ['$scope', '$http', '$routeParams', TypeDetailCtrl]);

function ProtoDetailCtrl($scope, $http, $routeParams) {
    var protoId;
    $scope.protoId = protoId = $routeParams.protoId;
    $http.get('data/proto/' + protoId + '.json')
        .success(function (data) {
            $scope.proto = data;
        });
}
controllers.controller('ProtoDetailCtrl', ['$scope', '$http', '$routeParams', ProtoDetailCtrl]);


function SearchCtrl($log, $location, TreeService, $scope) {
    var self = this;

    self.states = {};
    self.tree = TreeService.getTree();

    self.filterItems = filterItems;
    self.selectedItemChange = selectedItemChange;
    self.mapToLowerCase = mapToLowerCase;
    var options = {
        shouldSort: true,
        tokenize: true,
        matchAllTokens: true,
        maxPatternLength: 32,
        keys: [
            "label",
            "ancronym"
        ]
    };
    var fuse = {};
    $scope.$on('treeData:updated', function (event, data) {
        self.states = searchFunction(TreeService.getTreeData(), []);
        fuse = new Fuse(self.states, options);
    });


    function filterItems(query) {
        return query ? fuse.search(query) : self.states;
    }

    function selectedItemChange(item) {
        self.tree.collapse_all();
        if (item) {
            self.tree.select_branch(item);
            if (item.data.type === "proto") {
                $location.path('/protos/' + item.data.ref);
            } else {
                $location.path('/types/' + item.data.ref);
            }
            $log.info('Item changed to ' + JSON.stringify(item));
        }
    }

    function mapToLowerCase(word) {
        return angular.lowercase(word);
    }

    function searchFunction(node, result) {
        for (var key in node) {
            if (node[key] != null && node.hasOwnProperty(key) && typeof node[key] == "object") {
                searchFunction(node[key], result);
                if (node[key].label && !node[key].label.includes(".proto")) {
                    node[key].ancronym = node[key].label.match(/(?=[A-Z])(\w)/g).join('');
                    result.push(node[key]);
                }
            }
        }
        return result;
    }
}
controllers.controller('SearchCtrl', ['$log', '$location', 'TreeService', '$scope', SearchCtrl]);