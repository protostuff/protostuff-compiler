var factories = angular.module('factories', []);

function ProtoDataFactory($http) {
    var protoDataService = {
        getTypeIndex: function () {
            return $http.get('data/index.json').then(function (response) {
                return response.data;
            });
        },

        getPageIndex: function () {
            return $http.get('data/pages.json').then(function (response) {
                return response.data;
            });
        }
    };
    return protoDataService;
}

factories.factory('ProtoDataFactory', ['$http', ProtoDataFactory]);

function TreeService($rootScope) {
    var tree = {};
    var treeData = [];

    var setTreeData = function (newTreeData) {
        treeData = newTreeData;
        $rootScope.$broadcast('treeData:updated', treeData);
    };

    var getTree = function () {
        return tree;
    };

    var getTreeData = function () {
        return treeData;
    };

    return {
        getTree: getTree,
        getTreeData: getTreeData,
        setTreeData: setTreeData
    };
}

factories.service('TreeService', ['$rootScope', TreeService]);