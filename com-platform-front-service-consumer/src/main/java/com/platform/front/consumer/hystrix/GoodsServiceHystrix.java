package com.platform.front.consumer.hystrix;

import com.platform.front.consumer.client.param.TpwdParam;
import com.platform.common.modal.PageData;
import com.platform.front.consumer.client.param.FindGoodsListParam;

import com.platform.common.modal.goods.GoodsInfo;
import com.platform.front.consumer.feignClient.GoodsService;

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
    public PageData<GoodsInfo> findGoodsLists(FindGoodsListParam param){
        PageData<GoodsInfo> result=new PageData<>();
        result.setSuccess(false);
        result.setMsg("超时熔断");
        return result;
    }
   @Override
   public String getTpwd(TpwdParam tpwdParam){
        return "请求超时";
   }
}
