function getDay(day) {
    if (day.length == 2) {
        return day;
    }
    return "0" + day;
}

function loadCategorys() {
    $.ajax({
        url: "/getMyCategorys",
        type: "POST",
        dataType: "json",
        success: function (result) {
            if (result.success) {
                var html = "";
                if (result.data != null) {
                    for (var i = 0; i < result.data.length; i++) {
                        if (i % 3 == 0) {
                            html = html + "<br/>";
                        }
                        html = html + "<input type=\"checkbox\" name=\"category\" value=\"" + result.data[i].myCategoryId + "\" />" + result.data[i].myCategoryName + "&nbsp";
                    }
                }
                $("#categoryDiv").html(html);
                return;
            } else {
                alert(result.msg);
                return;
            }
        }, error: function () {
            alert('系统错误！');
            return;
        }
    });
}

$(document).ready(function () {

    var day2 = new Date();
    day2.setTime(day2.getTime());
    var s2 = day2.getFullYear() + "-" + getDay(day2.getMonth() + 1) + "-" + day2.getDate();
    $('#endTime').val(s2);

    loadCategorys();

    $('#endTime').mobiscroll().date({
        lang: 'zh',
        theme: 'material',
        dateFormat: 'yyyy-mm-dd',
        endYear: 2030,
        startYear: 2019,
        display: 'center'
    });

    $("#pushMsgBtn").click(function () {
        var msg = $("#msg").val();
        var platform = "";
        $('input[name="platform"]:checked').each(function () {
            if (platform.length > 0) {
                platform = platform + ",";
            }
            platform = platform + $(this).val();
        });
        var category = "";
        $('input[name="category"]:checked').each(function () {
            if (category.length > 0) {
                category = category + ",";
            }
            category = category + $(this).val();
        });
        if (msg == null || msg.length == 0) {
            alert("请填写消息内容");
            return;
        }
        if (platform == null || platform.length == 0) {
            alert("请选择商品所属平台");
            return;
        }
        if (category == null || category.length == 0) {
            alert("请选择商品分类");
            return;
        }
        var endDate = $("#endTime").val();
        if (endDate == null || endDate.length == 0) {
            alert("请选择截止日期");
            return;
        }
        var endDay = convertDateFromString(endDate);
        var dateNow = new Date();
        var hour = (endDay.getTime() - dateNow.getTime()) / (1000 * 60 * 60);
        if (hour <= 0) {
            alert("截至日期必须大于现在时间");
            return;
        }
        $.ajax({
            url: "/pushMsg",
            data: {
                msg: msg,
                platform: platform,
                category: category,
                endTime: endDate,
                maxSendCount: $("#maxSendCount").val()
            },
            type: "POST",
            dataType: "json",
            success: function (result) {
                if (result.success) {
                    alert("发布成功");
                    return;
                } else {
                    alert(result.msg);
                    return;
                }
            }, error: function () {
                alert('系统错误！');
                return;
            }
        });
    });

});


/**
 * 输入的时间格式为yyyy-MM-dd
 * @param dateString
 * @returns {Date}
 */
function convertDateFromString(dayString) {
    if (dayString) {
        var sdate = dayString.split('-');
        var date = new Date(sdate[0], sdate[1] - 1, sdate[2], 23, 59, 59);
        return date;
    }
}