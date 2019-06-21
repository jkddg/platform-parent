package com.platform.front.service.hystrix;

import com.platform.common.modal.PageData;
import com.platform.front.service.client.param.FindGoodsListParam;
import com.platform.front.service.iface.GoodsService;
import com.platform.front.service.modal.GoodsInfo;

/**
 * Created by Huangyonghao on 2019/6/21 17:26.
 */
public class GoodsServiceHystrix implements GoodsService {
    @Override
   public PageData<GoodsInfo> findGoodsList(FindGoodsListParam param){
        PageData<GoodsInfo> result=new PageData<>();
        result.setSuccess(false);
        result.setMsg("超时熔断");
        return result;
   }
}
