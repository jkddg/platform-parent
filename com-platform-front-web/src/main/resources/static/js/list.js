var currentPage = 1;
$(document).ready(function () {

    var $container = $('.masonry-container');
    $container.imagesLoaded(function () {
        $container.masonry({
            columnWidth: '.item',
            itemSelector: '.item'
        });
    });
//Reinitialize masonry inside each panel after the relative tab link is clicked -
    $('a[data-toggle=tab]').each(function () {
        var $this = $(this);
        $this.on('shown.bs.tab', function () {
            $container.imagesLoaded(function () {
                $container.masonry({
                    columnWidth: '.item',
                    itemSelector: '.item'
                });
            });
        }); //end shown
    }); //end each
    $("#searchBtn").click(function () {
        getData(currentPage);
    });
});

function getData(page) {

    var keyWord = $("#keyWord").val();
    var sort = $("input[name=sort]:checked").val();

    var platform = "";
    $("input[name='platform']:checked").each(function (i) {
        if (platform.length > 0) {
            platform = platform + ",";
        }
        platform = platform + $(this).val();
    });
    $.ajax({
        url: "/getData",
        data: {
            "keyWord": keyWord,
            "platform": platform,
            "sort": sort,
            "page": page
        },
        type: "POST",
        dataType: "json",
        success: function (result) {
            if (!result.success) {
                alert(result.msg);
            }
            for (var i = 0; i < result.data.length; i++) {
                var data = result.data[i];
                var item = "<div class=\"col-md-4 col-sm-6 item\">";
                item = item + "<div class=\"thumbnail\">";
                item = item + "<img src=\"" + data.pictUrl + "\" alt=\"\">";
                item = item + "<div class=\"caption\">";
                item = item + "<h3>" + data.shortTitle + "</h3>";
                item = item + "<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. </p>";
                // item = item + "<p><a href=\"#\" class=\"btn btn-primary\" role=\"button\">Button</a> <a href=\"#\" class=\"btn btn-default\" role=\"button\">Button</a></p>";
                item = item + "</div></div></div>";
                $("#tbList").append(item);
            }
        }, error: function () {
            alert('查询失败！');
        }
    });
}




