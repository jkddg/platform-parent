package com.platform.front.service.modal;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created by Huangyonghao on 2019/6/18 13:15.
 */
@Getter
@Setter
public class GoodsInfo implements Serializable {

    private String platform;

    private String plat;

    /**
     * 商品信息-叶子类目id
     */

    private Long categoryId;
    /**
     * 商品信息-叶子类目名称
     */

    private String categoryName;
    /**
     * 商品信息-佣金比率。1550表示15.5%
     */

    private String commissionRate;
    /**
     * 商品信息-佣金类型。MKT表示营销计划，SP表示定向计划，COMMON表示通用计划
     */

    private String commissionType;
    /**
     * 优惠券信息-优惠券面额。如：满299元减20元
     */

    private String couponAmount;
    /**
     * 优惠券信息-优惠券结束时间
     */

    private String couponEndTime;
    /**
     * 优惠券信息-优惠券id
     */

    private String couponId;
    /**
     * 优惠券信息-优惠券满减信息
     */

    private String couponInfo;
    /**
     * 优惠券信息-优惠券剩余量
     */

    private Long couponRemainCount;
    /**
     * 链接-宝贝+券二合一页面链接
     */

    private String couponShareUrl;
    /**
     * 优惠券信息-优惠券起用门槛，满X元可用。如：满299元减20元
     */

    private String couponStartFee;
    /**
     * 优惠券信息-优惠券开始时间
     */

    private String couponStartTime;
    /**
     * 优惠券信息-优惠券总量
     */

    private Long couponTotalCount;


    /**
     * 商品信息-宝贝描述(推荐理由)
     */

    private String itemDescription;
    /**
     * 商品信息-宝贝id
     */

    private Long itemId;
    /**
     * 链接-宝贝地址
     */

    private String itemUrl;

    /**
     * 商品信息-一级类目ID
     */

    private Long levelOneCategoryId;
    /**
     * 商品信息-一级类目名称
     */

    private String levelOneCategoryName;
    /**
     * 店铺信息-卖家昵称
     */

    private String nick;


    /**
     * 商品信息-商品主图
     */

    private String pictUrl;
    /**
     * 商品信息-宝贝所在地
     */

    private String provcity;
    /**
     * 商品信息-商品一口价格
     */

    private String reservePrice;

    /**
     * 店铺信息-卖家id
     */

    private Long sellerId;
    /**
     * 店铺信息-店铺dsr评分
     */

    private Long shopDsr;
    /**
     * 店铺信息-店铺名称
     */

    private String shopTitle;
    /**
     * 商品信息-商品短标题
     */

    private String shortTitle;

    /**
     * 商品信息-商品标题
     */

    private String title;


    /**
     * 链接-宝贝推广链接
     */

    private String url;
    /**
     * 店铺信息-卖家类型。0表示集市，1表示天猫
     */

    private Long userType;
    /**
     * 预售专用-预售数量
     */

    private Long uvSumPreSale;
    /**
     * 商品信息-30天销量
     */

    private Long volume;
    /**
     * 商品信息-商品白底图
     */

    private String whiteImage;

    /**
     * 商品信息-商品折扣价格
     */

    private String zkFinalPrice;

    public String getFinalPrice() {
        return String.format("%.2f", (Double.parseDouble(zkFinalPrice) - Double.parseDouble(couponAmount)));
    }
}
