package com.platform.admin.service.api;

import com.jd.open.api.sdk.DefaultJdClient;
import com.jd.open.api.sdk.JdClient;
import com.jd.open.api.sdk.JdException;
import com.platform.admin.service.config.JdConfig;
import com.platform.common.util.SecurityUtil;
import jd.union.open.goods.jingfen.query.request.JFGoodsReq;
import jd.union.open.goods.jingfen.query.request.UnionOpenGoodsJingfenQueryRequest;
import jd.union.open.goods.jingfen.query.response.UnionOpenGoodsJingfenQueryResponse;
import jd.union.open.order.query.request.OrderReq;
import jd.union.open.order.query.request.UnionOpenOrderQueryRequest;
import jd.union.open.order.query.response.UnionOpenOrderQueryResponse;
import jd.union.open.promotion.common.get.request.UnionOpenPromotionCommonGetRequest;
import jd.union.open.promotion.common.get.response.UnionOpenPromotionCommonGetResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Slf4j
@Component
public class JdAPI {

    @Autowired
    JdConfig jdConfig;

    protected JdClient getClient() {
        String appKey = null;
        String appSecret = null;
        try {
            appKey = SecurityUtil.desDecrypt(jdConfig.getAppKey());
            appSecret = SecurityUtil.desDecrypt(jdConfig.getAppSecret());
        } catch (Exception e) {
            e.printStackTrace();
        }
        JdClient client = new DefaultJdClient(jdConfig.getServerUrl(), jdConfig.getAccessToken(), appKey, appSecret);
        return client;
    }

    /**
     * 京粉精选商品查询接口
     *
     * @param eliteId   商品活动分类
     * @param pageSize
     * @param pageIndex
     * @param sortName
     * @param sort
     * @return
     */
    public UnionOpenGoodsJingfenQueryResponse queryJinfenGoods(int eliteId, int pageSize, int pageIndex, String sortName, String sort) {

        JdClient client = this.getClient();
        UnionOpenGoodsJingfenQueryRequest request = new UnionOpenGoodsJingfenQueryRequest();
        JFGoodsReq goodsReq = new JFGoodsReq();
        goodsReq.setEliteId(eliteId);
        goodsReq.setPageSize(pageSize);
        goodsReq.setPageIndex(pageIndex);
        goodsReq.setSortName(sortName);
        goodsReq.setSort(sort);
        request.setGoodsReq(goodsReq);
        UnionOpenGoodsJingfenQueryResponse response = null;
        try {
            response = client.execute(request);
        } catch (JdException e) {
            e.printStackTrace();
        }
        return response;
    }

    /**
     * 订单查询
     *
     * @param type      订单时间查询类型(1：下单时间，2：完成时间，3：更新时间)
     * @param time      查询时间，建议使用分钟级查询，格式：yyyyMMddHH、yyyyMMddHHmm或yyyyMMddHHmmss，如201811031212 的查询范围从12:12:00--12:12:59
     * @param pageSize
     * @param pageIndex
     * @return
     */
    public UnionOpenOrderQueryResponse queryOrders(int type, String time, int pageSize, int pageIndex) {
        JdClient client = this.getClient();
        UnionOpenOrderQueryRequest unionOpenOrderQueryRequest = new UnionOpenOrderQueryRequest();
        OrderReq orderReq = new OrderReq();
        orderReq.setPageNo(pageIndex);
        orderReq.setPageSize(pageSize);
        orderReq.setType(type);
        orderReq.setTime(time);
        unionOpenOrderQueryRequest.setOrderReq(orderReq);
        UnionOpenOrderQueryResponse response = null;
        try {
            response = client.execute(unionOpenOrderQueryRequest);
        } catch (JdException e) {
            e.printStackTrace();
        }
        return response;
    }

    /**
     * 获取通用推广链接（转链接口）
     *
     * @param itemUrl   商品链接
     * @param couponUrl 优惠券领取链接
     * @param siteId
     * @param ext1
     * @return
     */
    public UnionOpenPromotionCommonGetResponse getPromotionUrl(String itemUrl, String couponUrl, String siteId, String ext1) {
        JdClient client = this.getClient();
        UnionOpenPromotionCommonGetRequest request = new UnionOpenPromotionCommonGetRequest();
        jd.union.open.promotion.common.get.request.PromotionCodeReq promotionCodeReq = new jd.union.open.promotion.common.get.request.PromotionCodeReq();
        promotionCodeReq.setMaterialId(itemUrl);
        promotionCodeReq.setCouponUrl(couponUrl);
        promotionCodeReq.setSiteId(siteId);
        if (!StringUtils.isEmpty(ext1)) {
            promotionCodeReq.setExt1(ext1);
        }
        request.setPromotionCodeReq(promotionCodeReq);
        UnionOpenPromotionCommonGetResponse response = null;
        try {
            response = client.execute(request);
        } catch (JdException e) {
            e.printStackTrace();
        }
        return response;
    }
}
