package com.platform.front.service.mapper;

import com.platform.common.contanst.PlatformEnum;
import com.platform.front.service.modal.GoodsInfo;
import com.taobao.api.response.TbkDgItemCouponGetResponse;
import com.taobao.api.response.TbkDgMaterialOptionalResponse;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Huangyonghao on 2019/6/18 13:25.
 */
public class TaobaoGoodsMapper {


    public static GoodsInfo convertToGoodsInfo(TbkDgMaterialOptionalResponse.MapData info) {
        if (info == null) {
            return null;
        }
        GoodsInfo goodsInfo = new GoodsInfo();
        BeanUtils.copyProperties(info, goodsInfo);
        goodsInfo.setZkFinalPrice(Double.valueOf(info.getZkFinalPrice()));
        goodsInfo.setReservePrice(Double.valueOf(info.getReservePrice()));
        goodsInfo.setCouponAmount(Double.valueOf(info.getCouponAmount()));
        goodsInfo.setCouponStartFee(Double.valueOf(info.getCouponStartFee()));
        goodsInfo.setCouponStartTime(LocalDateTime.parse(info.getCouponStartTime()));
        goodsInfo.setPlatform(info.getUserType() == 0 ? PlatformEnum.TAOBAO.displayName() : PlatformEnum.TMALL.displayName());
        goodsInfo.setPlat(info.getUserType() == 0 ? PlatformEnum.TAOBAO.value() : PlatformEnum.TMALL.value());
        return goodsInfo;
    }

    public static List<GoodsInfo> convertToGoodsInfos(List<TbkDgMaterialOptionalResponse.MapData> list) {
        List<GoodsInfo> result = new ArrayList<>();
        if (list == null || list.size() == 0) {
            return result;
        }
        for (int i = 0; i < list.size(); i++) {
            GoodsInfo info = convertToGoodsInfo(list.get(i));
            if (info != null) {
                result.add(info);
            }
        }
        return result;
    }

    public static GoodsInfo convertCouponInfo(TbkDgItemCouponGetResponse.TbkCoupon info) {
        if (info == null) {
            return null;
        }
        GoodsInfo goodsInfo = new GoodsInfo();
        BeanUtils.copyProperties(info, goodsInfo);
        goodsInfo.setPlatform(info.getUserType() == 0 ? "淘宝" : "天猫");
        goodsInfo.setCouponShareUrl(info.getCouponClickUrl());
        goodsInfo.setShortTitle(info.getTitle());
        return goodsInfo;
    }

    public static List<GoodsInfo> convertConponInfos(List<TbkDgItemCouponGetResponse.TbkCoupon> list) {
        List<GoodsInfo> result = new ArrayList<>();
        if (list == null || list.size() == 0) {
            return result;
        }
        for (int i = 0; i < list.size(); i++) {
            GoodsInfo info = convertCouponInfo(list.get(i));
            if (info != null) {
                result.add(info);
            }
        }
        return result;
    }
}
