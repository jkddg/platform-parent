package com.platform.front.web.controller.site;

import com.platform.common.contanst.PlatformEnum;
import com.platform.common.modal.goods.GoodsInfo;
import com.platform.common.modal.PageData;
import com.platform.front.service.client.param.FindGoodsListParam;
import com.platform.front.service.client.param.TpwdParam;
import com.platform.front.service.iface.GoodsService;
import com.platform.front.web.controller.param.DataListParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Huangyonghao on 2019/6/20 15:33.
 */
@Controller
public class IndexController {

    @Autowired
    GoodsService goodsService;

    @RequestMapping({"", "/index"})
    public ModelAndView indexPage() {
        ModelAndView view = new ModelAndView();
        view.setViewName("view/index");
        view.addObject("pid", "mm_10869508_621300027_109172500468");
        return view;
    }

    @RequestMapping("getData")
    @ResponseBody
    public PageData<GoodsInfo> getData(DataListParam dataListParam) {
        FindGoodsListParam findGoodsListParam = new FindGoodsListParam();
        List<PlatformEnum> platformEnums = new ArrayList<>();
        if (!StringUtils.isEmpty(dataListParam.getPlatform())) {
            if (dataListParam.getPlatform().indexOf("tb") > -1) {
                platformEnums.add(PlatformEnum.TAOBAO);
                platformEnums.add(PlatformEnum.TMALL);
            }
            if (dataListParam.getPlatform().indexOf("jd") > -1) {
                platformEnums.add(PlatformEnum.JD);
            }
            if (dataListParam.getPlatform().indexOf("pdd") > -1) {

            }
        }
        findGoodsListParam.setPlatformEnum(platformEnums);
        findGoodsListParam.setKeyWord(dataListParam.getKeyWord());
        findGoodsListParam.setSort(dataListParam.getSort());
        findGoodsListParam.setPageIndex(dataListParam.getPage());
        PageData<GoodsInfo> result = goodsService.findGoodsLists(findGoodsListParam);
        return result;
    }

    @RequestMapping("getTpwd")
    @ResponseBody
    public String getTpwd(TpwdParam tpwdParam) {
        String result = goodsService.getTpwd(tpwdParam);
        return result;
    }


}
