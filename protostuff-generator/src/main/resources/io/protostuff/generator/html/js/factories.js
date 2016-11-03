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
        treeData = searchFunction(newTreeData, []);
        $rootScope.$broadcast('treeData:updated', treeData);
    };

    var getTree = function () {
        return tree;
    };

    var getTreeData = function () {
        return treeData;
    };

    var getFuzzySearchResult = function (needle, haystack) {
        var hlen = haystack.length;
        var nlen = needle.length;
        if (nlen > hlen) {
            return false;
        }
        if (nlen === hlen) {
            return needle === haystack;
        }
        outer: for (var i = 0, j = 0; i < nlen; i++) {
            var nch = needle.charCodeAt(i);
            while (j < hlen) {
                if (haystack.charCodeAt(j++) === nch) {
                    continue outer;
                }
            }
            return false;
        }
        return true;
    };

    function searchFunction(node, result) {
        for (var key in node) {
            if (node[key] != null && node.hasOwnProperty(key) && typeof node[key] == "object") {
                searchFunction(node[key], result);
                if (node[key].label && !node[key].label.includes(".proto")) {
                    result.push(node[key]);
                }
            }
        }
        return result;
    }

    return {
        getTree: getTree,
        getTreeData: getTreeData,
        setTreeData: setTreeData,
        getFuzzySearchResult: getFuzzySearchResult
    };
}

factories.service('TreeService', ['$rootScope', TreeService]);