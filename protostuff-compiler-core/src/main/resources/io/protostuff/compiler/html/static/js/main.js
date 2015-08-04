if (typeof String.prototype.startsWith != 'function') {
    String.prototype.startsWith = function (str){
        return this.slice(0, str.length) == str;
    };
}

function clearSearch() {
    $('#tree').treeview('clearSearch');
    $('#input-search').val('');
}

$(function() {
    var content = $("#content");
    //set up hash detection
    $(window).bind( 'hashchange', function(e) {
        var hash = location.hash;
        if (hash.startsWith("#page=")) {
            var type = hash.substring(6, hash.length);
            if (type !== "") {
                var lastPage = localStorage.getItem("lastPage");
                if (lastPage == "search") {
                    clearSearch();
                }
                localStorage.setItem("lastPage", "page");
                document.title = type;
                content.load(type + ".html");
                $.each($('#tree').treeview(true).getUnselected(), function (key, value) {
                    if (hash === value.href) {
                        var $tree = $('#tree');
                        $tree.treeview('selectNode', [value.nodeId, {silent: true}]);
                        $tree.treeview('revealNode', [ value.nodeId, { silent: true } ]);
                    }
                });
                return;
            }
        } else if (hash.startsWith("#search=")) {
            var pattern = hash.substring(8, hash.length);
            if (pattern !== "") {
                localStorage.setItem("lastPage", "search");
                document.title = "Search Results for " + pattern;
                var options = {
                    ignoreCase: true,
                    exactMatch: false,
                    revealResults: true
                };
                var $tree = $('#tree');
                var results = $tree.treeview('search', [ pattern, options ]);
                var output = '<p>' + results.length + ' matches found</p>\n';
                output += '<ul>\n';
                $.each(results, function (index, result) {
                    output += '<li><a href="' + result.href + '">' + result.text + '</a>\n';
                });
                output += '</ul>\n';
                $('#content').html(output);
                $('#input-search').val(pattern);
                return;
            }
        }
        location.hash = '#page=main';
        document.title = 'Protocol Documentation';
        content.load("main.html");
        clearSearch();
    });
    $(window).trigger( 'hashchange' );
});

$("#input-search").keyup(function(event){
    if(event.keyCode == 13){
        $("#btn-search").click();
    }
});

$('#btn-search').on('click', function(e) {
    var pattern = $('#input-search').val();
    window.location.hash = '#search=' + pattern;
});

var tree = $('#tree');

tree.treeview({
    data: getTree(),
    showBorder: false,
    showTags: true,
    color: "#428bca"
});

tree.on('nodeSelected', function(event, data) {
    window.location.hash = data.href;
});