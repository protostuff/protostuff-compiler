var factories = angular.module('factories', []);

function ProtoDataFactory($http) {
    return {
        getTypeIndex: function () {
            return $http.get('data/index.json').then(function (response) {
                return response.data;
            });
        },
        getPageIndex: function () {
            return $http.get('data/pages.json').then(function (response) {
                return response.data;
            });
        },
        getType: function (typeId) {
            return $http.get('data/type/' + typeId + '.json').then(function (response) {
                return response.data;
            });
        },
        getPage: function (pageId) {
            return $http.get('data/pages/' + pageId + '.json').then(function (response) {
                return response.data;
            });
        },
        getProtoDetail: function (protoDetailId) {
            return $http.get('data/proto/' + protoDetailId + '.json').then(function (response) {
                return response.data;
            });
        }
    };
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