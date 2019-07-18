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
        $("#tbList").empty();
        getData(currentPage);
    });

    var range = 50;             //距下边界长度/单位px
    var elemt = 500;           //插入元素高度/单位px
    var maxnum = 20;            //设置加载最多次数
    var num = 1;
    var totalheight = 0;
    var main = $("#tabpanel");                     //主体元素
    $(window).scroll(function () {
        var srollPos = $(window).scrollTop();    //滚动条距顶部距离(页面超出窗口的高度)
        totalheight = parseFloat($(window).height()) + parseFloat(srollPos);
        if (($(document).height() - range) <= totalheight && num != maxnum) {
            getData(currentPage);
        }
    });

});

function getData(page) {

    var keyWord = $("#keyWord").val();

    if (keyWord == "") {
        alert("请填写搜索关键词");
        return;
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

            for (var i = 0; i < result.data.length; i++) {
                var data = result.data[i];
                var item = "<div class=\"col-md-3 col-sm-4 col-xs-12 item\" style='padding-right:5px;padding-left:5px;'>";
                item = item + "<div class=\"box\" >";
                item = item + "<div class=\"box-con\">";
                item = item + "<div class=\"box-text\">" + data.couponAmount + "元券</div>";
                item = item + "<img src=\"" + data.pictUrl + "\" alt=\"" + data.title + "\" style='width: 100%;height: 100%;'>";
                item = item + "</div>";
                // item = item + "<p style='position: absolute;z-index: 2;left: 20px;top: 0px;color: red;font-size: x-large;'>" + data.couponAmount + "元券</p>";
                item = item + "<div class=\"caption\">";
                item = item + "<a href='" + data.couponShareUrl + "' target='_blank'><p>[" + data.platform + "]" + data.title + "</p></a>";
                // item = item + "<p>"+ data. +"</p>";
                item = item + "<p style='float: left;'>价格:<span style='color: #b21f2d'>" + data.zkFinalPrice + "</span>,券后价:<span style='color: red'>" + data.finalPrice + "</span></p>" +
                    "<p style='float: right;'><a href=\"###\" id='" + data.itemId + "' onclick='showItemPopover(\"" + data.platform + "\"," + data.itemId + ",\"" + data.shortTitle + "\",\"" + data.couponShareUrl + "\")'>口令分享</a></p>";
                item = item + "</div></div></div>";
                $("#tbList").append(item);
            }
            currentPage++;

        }, error: function () {
            alert('查询失败！');
        }
    });
}


function showItemPopover(platform, itemId, text, url) {

    $.ajax({
        url: "/getTpwd",
        data: {
            "itemId": itemId,
            "text": text,
            "url": url
        },
        type: "POST",
        dataType: "html",
        success: function (result) {
            if ($('#' + itemId).attr("aria-describedby") != undefined) {
                $('#' + itemId).popover('destroy')
            } else {
                $('#' + itemId).popover({
                    "placement": "auto",
                    "title": "长按复制",
                    "content": result
                }).popover('show');
            }
        }, error: function () {
            alert('查询失败！');
        }
    });

}



