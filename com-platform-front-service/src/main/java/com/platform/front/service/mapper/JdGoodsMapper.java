package com.platform.front.service.mapper;

import com.platform.common.contanst.PlatformEnum;
import com.platform.front.service.modal.GoodsInfo;
import jd.union.open.goods.jingfen.query.response.Coupon;
import jd.union.open.goods.jingfen.query.response.JFGoodsResp;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class JdGoodsMapper {

    public static GoodsInfo convertGoods(JFGoodsResp jfGoodsResp) {
        if (jfGoodsResp == null) {
            return null;
        }
        GoodsInfo goodsInfo = new GoodsInfo();
        goodsInfo.setShortTitle(jfGoodsResp.getSkuName());
        goodsInfo.setCouponShareUrl(jfGoodsResp.getMaterialUrl());
        goodsInfo.setPlatform(PlatformEnum.JD.displayName());
        goodsInfo.setPlat(PlatformEnum.JD.value());
        if (jfGoodsResp.getCategoryInfo() != null) {
            goodsInfo.setLevelOneCategoryId(jfGoodsResp.getCategoryInfo().getCid1());
            goodsInfo.setLevelOneCategoryName(jfGoodsResp.getCategoryInfo().getCid1Name());
            goodsInfo.setCategoryId(jfGoodsResp.getCategoryInfo().getCid2());
            goodsInfo.setCategoryName(jfGoodsResp.getCategoryInfo().getCid2Name());
        }
        if (jfGoodsResp.getCommissionInfo() != null) {
            goodsInfo.setCommissionRate(String.valueOf(jfGoodsResp.getCommissionInfo().getCommissionShare()));
//            goodsInfo.setCommissionType();
        }

        if (jfGoodsResp.getCouponInfo() != null && jfGoodsResp.getCouponInfo().getCouponList() != null && jfGoodsResp.getCouponInfo().getCouponList().length > 0) {
            for (Coupon coupon : jfGoodsResp.getCouponInfo().getCouponList()) {
                if (coupon.getPlatformType() == 0) {
                    goodsInfo.setCouponAmount(coupon.getDiscount());
                    goodsInfo.setCouponEndTime(LocalDateTime.ofInstant(Instant.ofEpochMilli(coupon.getGetEndTime()), ZoneId.systemDefault()));
                    goodsInfo.setCouponId("0");
                    goodsInfo.setCouponInfo("");
                    goodsInfo.setCouponRemainCount(1L);
                    goodsInfo.setCouponStartFee(coupon.getQuota());
                    goodsInfo.setCouponStartTime(LocalDateTime.ofInstant(Instant.ofEpochMilli(coupon.getGetStartTime()), ZoneId.systemDefault()));
                    goodsInfo.setCouponTotalCount(1L);
                    goodsInfo.setCouponShareUrl(coupon.getLink());
                }
            }
        }


        goodsInfo.setItemDescription(jfGoodsResp.getSkuName());
        goodsInfo.setItemId(jfGoodsResp.getSkuId());
        if (jfGoodsResp.getImageInfo() != null && jfGoodsResp.getImageInfo().getImageList() != null && jfGoodsResp.getImageInfo().getImageList().length > 0) {
            goodsInfo.setPictUrl(jfGoodsResp.getImageInfo().getImageList()[0].getUrl());
        }
        if (jfGoodsResp.getShopInfo() != null) {
            goodsInfo.setNick(jfGoodsResp.getShopInfo().getShopName());
        }
        goodsInfo.setItemUrl(jfGoodsResp.getMaterialUrl());
        goodsInfo.setProvcity("");
        goodsInfo.setTitle(jfGoodsResp.getSkuName());
        goodsInfo.setUrl(jfGoodsResp.getMaterialUrl());

        goodsInfo.setVolume(jfGoodsResp.getInOrderCount30Days());
        if (jfGoodsResp.getPriceInfo() != null) {
            goodsInfo.setZkFinalPrice(jfGoodsResp.getPriceInfo().getPrice());
        }
        return goodsInfo;
    }
}
