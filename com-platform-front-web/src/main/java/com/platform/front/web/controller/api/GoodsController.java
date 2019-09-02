package com.platform.front.web.controller.api;

import com.platform.common.contanst.PlatformEnum;
import com.platform.common.modal.GoodsInfo;
import com.platform.common.modal.PageData;
import com.platform.front.service.client.param.FindGoodsListParam;
import com.platform.front.service.iface.GoodsService;
import com.platform.front.web.controller.param.DataListParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Huangyonghao on 2019/6/18 13:31.
 */

@RequestMapping("/api")
@Controller
public class GoodsController {

    @Autowired
    GoodsService goodsService;

    @RequestMapping("getData")
    @ResponseBody
    public PageData<GoodsInfo> getData(DataListParam dataListParam) {
        FindGoodsListParam findGoodsListParam = new FindGoodsListParam();
        List<PlatformEnum> platformEnums = new ArrayList<>();
        platformEnums.add(PlatformEnum.TAOBAO);
        platformEnums.add(PlatformEnum.TMALL);
        //platformEnums.add(PlatformEnum.JD);
        findGoodsListParam.setPlatformEnum(platformEnums);
        findGoodsListParam.setKeyWord(dataListParam.getKeyWord());
        findGoodsListParam.setSort(dataListParam.getSort());
        findGoodsListParam.setPageIndex(dataListParam.getPage());
        findGoodsListParam.setPageSize(dataListParam.getPageSize());
        PageData<GoodsInfo> result = goodsService.findGoodsList(findGoodsListParam);
        return result;
    }
}
