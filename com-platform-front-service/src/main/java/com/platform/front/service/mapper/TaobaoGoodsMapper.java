package com.platform.front.service.mapper;

import com.platform.front.service.modal.GoodsInfo;
import com.platform.front.service.modal.TaobaoGoodsInfo;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Huangyonghao on 2019/6/18 13:25.
 */
public class TaobaoGoodsMapper {


    public static GoodsInfo convertToGoodsInfo(TaobaoGoodsInfo info) {
        if(info==null){
            return null;
        }
        GoodsInfo goodsInfo = new GoodsInfo();
        BeanUtils.copyProperties(info, goodsInfo);
        return goodsInfo;
    }

    public static List<GoodsInfo> convertToGoodsInfos(List<TaobaoGoodsInfo> list){
        List<GoodsInfo> result=new ArrayList<>();
        if(list==null || list.size()==0){
            return result ;
        }
        for(int i=0;i<list.size();i++){
            GoodsInfo info=convertToGoodsInfo(list.get(i));
            if(info!=null) {
                result.add(info);
            }
        }
        return result;
    }
}
