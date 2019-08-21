package com.platform.admin.service.mapper;

import com.platform.common.contanst.PlatformEnum;
import com.platform.common.modal.GoodsInfo;
import com.taobao.api.response.TbkDgMaterialOptionalResponse;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;

/**
 * Created by Huangyonghao on 2019/8/21 15:38.
 */
public class TbGoodsMapper {
    public static GoodsInfo convertGoods(TbkDgMaterialOptionalResponse.MapData data) {
        if (data == null) {
            return null;
        }
        GoodsInfo goodsInfo = new GoodsInfo();
        BeanUtils.copyProperties(data, goodsInfo);
        if (data.getUserType() == 1) {
            goodsInfo.setPlatformId(PlatformEnum.TMALL.value());
            goodsInfo.setPlatformName(PlatformEnum.TMALL.displayName());
        } else {
            goodsInfo.setPlatformId(PlatformEnum.TAOBAO.value());
            goodsInfo.setPlatformName(PlatformEnum.TAOBAO.displayName());
        }
        goodsInfo.setUpdateTime(LocalDateTime.now());
        return goodsInfo;
    }
}
