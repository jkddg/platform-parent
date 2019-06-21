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
        success: function (resultAu) {
            eval("var mydata=" + resultAu);

            var strCin = "<option value=\"\">请选择</option>";
            for (var int = 0; int < mydata.length; int++) {
                strCin += "<option value=\"" + mydata[int].dictType + ":" + mydata[int].dictName + ":" + mydata[int].dictCode + "\">" + mydata[int].dictName + "</option>";
            }
            $("#choseVouSellOrderCategory").html(strCin);
        }, error: function () {
            $alert('查询销售单分类失败！');
        }
    });
}




