var controllers = angular.module('controllers', []);

function TreeCtrl($location, ProtoDataFactory, TreeService) {
    var self = this;
    self.treeService = TreeService;
    self.treeData = [];
    self.pages = {};
    self.show = show;

    ProtoDataFactory.getTypeIndex().then(function (data) {
        self.treeData = data;
        TreeService.setTreeData(data);
    });

    ProtoDataFactory.getPageIndex().then(function (data) {
        self.pages = data;
    });

    function show(row) {
        if (row.data.type === "proto") {
            $location.path('/protos/' + row.data.ref);
        } else {
            $location.path('/types/' + row.data.ref);
        }
    }
}
controllers.controller('TreeCtrl',
    ['$location', 'ProtoDataFactory', 'TreeService', TreeCtrl]);

function TypeListCtrl(ProtoDataFactory) {

}
controllers.controller('TypeListCtrl', ['ProtoDataFactory', TypeListCtrl]);

function TypeDetailCtrl($routeParams, ProtoDataFactory) {
    var self = this;
    self.typeId = $routeParams.typeId;
    self.type = {};

    ProtoDataFactory.getType(self.typeId).then(function (data) {
        self.type = data;
    });
}
controllers.controller('TypeDetailCtrl', ['$routeParams', 'ProtoDataFactory', TypeDetailCtrl]);

function ProtoDetailCtrl($routeParams, ProtoDataFactory) {
    var self = this;
    self.protoId = $routeParams.protoId;
    self.proto = {};

    ProtoDataFactory.getProtoDetail(self.protoId).then(function (data) {
        self.proto = data;
    });
}
controllers.controller('ProtoDetailCtrl', ['$routeParams', 'ProtoDataFactory', ProtoDetailCtrl]);

function PageCtrl($routeParams, ProtoDataFactory) {
    var self = this;
    self.pageId = $routeParams.pageId;
    self.page = {};

    ProtoDataFactory.getPage(self.pageId).then(function (data) {
        self.page.name = data.name;
        self.page.content = data.content;
    });
}
controllers.controller('PageCtrl', ['$routeParams', 'ProtoDataFactory', PageCtrl]);

function SearchCtrl($location, TreeService, $scope) {
    var self = this;
    var fuse = {};

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
controllers.controller('SearchCtrl', ['$location', 'TreeService', '$scope', SearchCtrl]);