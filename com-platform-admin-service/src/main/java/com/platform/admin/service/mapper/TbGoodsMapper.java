package com.platform.admin.service.mapper;

import com.platform.common.contanst.PlatformEnum;
import com.platform.common.modal.GoodsInfo;
import com.platform.common.util.DateUtil;
import com.taobao.api.response.TbkDgMaterialOptionalResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

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
        if (!StringUtils.isEmpty(data.getReservePrice())) {
            goodsInfo.setReservePrice(Double.parseDouble(data.getReservePrice()));
        }
        if (!StringUtils.isEmpty(data.getZkFinalPrice())) {
            goodsInfo.setZkFinalPrice(Double.parseDouble(data.getZkFinalPrice()));
        }
        if (!StringUtils.isEmpty(data.getCouponAmount())) {
            goodsInfo.setCouponAmount(Double.parseDouble(data.getCouponAmount()));
        }
        if (goodsInfo.getCouponAmount() == null) {
            goodsInfo.setCouponAmount(0D);
        }
        if (!StringUtils.isEmpty(data.getCouponStartFee())) {
            goodsInfo.setCouponStartFee(Double.parseDouble(data.getCouponStartFee()));
        }
        if (!StringUtils.isEmpty(data.getCouponStartTime())) {
            goodsInfo.setCouponStartTime(DateUtil.dateStr2StartLocalDateTime(data.getCouponStartTime()));
        }
        if (!StringUtils.isEmpty(data.getCouponEndTime())) {
            goodsInfo.setCouponEndTime(DateUtil.dateStr2EndLocalDateTime(data.getCouponEndTime()));
        }
        if ("0.00".equals(goodsInfo.getFinalPrice())) {
            return null;
        }
        if (!StringUtils.isEmpty(data.getCommissionRate())) {
            goodsInfo.setCommissionRate(Double.valueOf(data.getCommissionRate()).longValue());
        }
        if (goodsInfo.getZkFinalPrice() == null) {
            return null;
        }
        goodsInfo.setId(goodsInfo.getPlatformId() + "_" + goodsInfo.getItemId());
        if (goodsInfo.getCouponAmount() > 0 && goodsInfo.getZkFinalPrice() > 0) {
            goodsInfo.setCouponRate(Double.valueOf(goodsInfo.getCouponAmount() * 10000 / (goodsInfo.getCouponAmount() + goodsInfo.getZkFinalPrice())).longValue());
        }
        return goodsInfo;
    }
}
