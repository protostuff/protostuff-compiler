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

    self.states = {};
    self.tree = TreeService.getTree();

    self.filterItems = filterItems;
    self.selectedItemChange = selectedItemChange;
    self.mapToLowerCase = mapToLowerCase;
    self.manualSearch = manualSearch;

    $scope.$on('treeData:updated', function (event, data) {
        self.states = TreeService.getTreeData();
    });

    function filterItems(searchText) {
        return searchText ? self.states.filter(function (item) {
            return TreeService.getFuzzySearchResult(searchText, item.label);
        }) : self.states;
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

    function manualSearch() {
        $location.path('/search/' + self.searchText);
    }
}
controllers.controller('SearchCtrl', ['$location', 'TreeService', '$scope', SearchCtrl]);

function SearchResultCtrl($routeParams, TreeService) {
    var self = this;
    self.searchText = $routeParams.searchText;

    self.filteredData = TreeService.getTreeData().filter(function (item) {
        return TreeService.getFuzzySearchResult(self.searchText, item.label)
    });
}
controllers.controller('SearchResultCtrl', ['$routeParams', 'TreeService', SearchResultCtrl]);