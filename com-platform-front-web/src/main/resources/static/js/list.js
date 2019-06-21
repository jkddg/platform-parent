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

    if (keyWord == "") {
        alert("请填写搜索关键词");
    }

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
            $("#tbList").empty();
            for (var i = 0; i < result.data.length; i++) {
                var data = result.data[i];
                var item = "<div class=\"col-md-3 col-sm-4 col-xs-12 item\">";
                item = item + "<div class=\"thumbnail\" style='z-index: 1;'>";
                item = item + "<img src=\"" + data.pictUrl + "\" alt=\"" + data.title + "\" style='width: 100%;height: 100%;'>";
                // item = item + "<p style='position: absolute;z-index: 2;left: 20px;top: 0px;'>" + data.platform + "</p>";
                item = item + "<div class=\"caption\">";
                item = item + "<a href='" + data.couponShareUrl + "' target='_blank'><h5>[" + data.platform + "]" + data.title + "</h5></a>";
                // item = item + "<p>"+ data. +"</p>";
                item = item + "<p align='center'><a href=\"#\" id='" + data.itemId + "' class=\"btn btn-default\" role=\"button\" onclick='showItemPopover(\"" + data.platform + "\"," + data.itemId + ")'>口令分享</a></p>";
                item = item + "</div></div></div>";
                $("#tbList").append(item);
            }
        }, error: function () {
            alert('查询失败！');
        }
    });
}


function showItemPopover(platform, itemId) {
    $('#' + itemId).popover({
        "placement": "auto",
        "title": "请复制",
        "content": "口令内容"
    });
    // $('#' + itemId).popover('show');

}



