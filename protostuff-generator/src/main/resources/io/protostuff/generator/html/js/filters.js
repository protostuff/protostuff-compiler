angular.module('filters', [])
    .filter('link', function () {
        return function (typeId) {
            return '<a href="#/types/' + typeId + '">' + typeId + '</a>';
        };
    });
