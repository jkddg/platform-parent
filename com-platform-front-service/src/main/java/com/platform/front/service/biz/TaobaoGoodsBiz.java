package com.platform.front.service.biz;

import com.platform.common.modal.PageData;
import com.platform.front.service.api.TaobaoGoodsAPI;
import com.platform.front.service.client.param.FindGoodsListParam;
import com.platform.front.service.mapper.TaobaoGoodsMapper;
import com.platform.front.service.modal.GoodsInfo;
import com.taobao.api.response.TbkDgMaterialOptionalResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Huangyonghao on 2019/6/18 14:56.
 */
@Slf4j
@Component
public class TaobaoGoodsBiz {

    @Autowired
    TaobaoGoodsAPI taobaoGoodsAPI;

    /**
     * 从API查询淘宝商品列表
     *
     * @param param
     * @return
     */
    public PageData<GoodsInfo> findGoodsList(FindGoodsListParam param) {
        PageData<GoodsInfo> result = new PageData<>();
        if (param == null) {
            result.setSuccess(false);
            result.setMsg("请求参数为空");
            return result;
        }
        TbkDgMaterialOptionalResponse response = taobaoGoodsAPI.tbkMaterialOptional(true, null, param.getKeyWord(), param.getPageSize(), param.getPageIndex());
        if (!response.isSuccess()) {
            result.setSuccess(false);
            result.setMsg(response.getMsg() + response.getSubMessage());
            log.info("请求淘宝API失败，返回信息：" + response.getBody());
            return result;
        }
        result.setSuccess(response.isSuccess());
        result.setTotalCount(response.getTotalResults());
        result.setData(TaobaoGoodsMapper.convertToGoodsInfos(response.getResultList()));
        return result;
    }

}
