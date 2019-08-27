package com.platform.front.service.api;

import com.platform.common.util.SecurityUtil;
import com.platform.front.service.config.TaobaoConfig;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.TbkDgMaterialOptionalRequest;
import com.taobao.api.request.TbkTpwdCreateRequest;
import com.taobao.api.response.TbkDgMaterialOptionalResponse;
import com.taobao.api.response.TbkTpwdCreateResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * Created by Huangyonghao on 2019/6/18 14:49.
 */
@Slf4j
@Component
public class TaobaoGoodsAPI  {

    @Autowired
    TaobaoConfig taobaoConfig;


    protected TaobaoClient getTaobaoClient(){

        String appKey = null;
        String appSecret = null;
        try {
            appKey = SecurityUtil.desDecrypt(taobaoConfig.getTbkAppkey());
            appSecret = SecurityUtil.desDecrypt(taobaoConfig.getTbkSecret());
        } catch (Exception e) {
            e.printStackTrace();
        }
        TaobaoClient client = new DefaultTaobaoClient(taobaoConfig.getTbkUrl(), appKey, appSecret);
        return client;

    }

    /**
     * 淘宝商品列表抓取
     *
     * @param pageSize
     * @param pageIndex
     */
    public TbkDgMaterialOptionalResponse tbkMaterialOptional(boolean hasCoupon, String categorys, String keyWord, long pageSize, long pageIndex, String sort, long materialId) {
        TaobaoClient client = getTaobaoClient();
        long adzoneId = taobaoConfig.getTkbAdzoneId();
        TbkDgMaterialOptionalRequest req = new TbkDgMaterialOptionalRequest();
//        req.setAdzoneId(appPropertiesConfig.getTaobaoConfig().getTkbAdzoneId());
//        req.setAdzoneId(103406600438L);
        req.setAdzoneId(adzoneId);
        req.setPageSize(pageSize);
        req.setPageNo(pageIndex);
        req.setPlatform(2L);
        req.setHasCoupon(hasCoupon);
        if (!StringUtils.isEmpty(categorys)) {
            req.setCat(categorys);
        }
        if (!StringUtils.isEmpty(keyWord)) {
            req.setQ(keyWord);
        }
        req.setSort(sort);
        if (materialId > 0) {
            req.setMaterialId(materialId);
        }
//        req.setStartDsr(10L);
//        req.setEndTkRate(1234L);
//        req.setStartTkRate(1234L);
//        req.setEndPrice(10L);
//        req.setStartPrice(10L);
//        req.setIsOverseas(false);
//        req.setIsTmall(false);

//        req.setItemloc("杭州");

//        req.setQ("女装");

//        req.setHasCoupon(false);
//        req.setIp("13.2.33.4");
//        req.setNeedFreeShipment(true);
//        req.setNeedPrepay(true);
//        req.setIncludePayRate30(true);
//        req.setIncludeGoodRate(true);
//        req.setIncludeRfdRate(true);
//        req.setNpxLevel(2L);
//        req.setEndKaTkRate(1234L);
//        req.setStartKaTkRate(1234L);
//        req.setDeviceEncrypt("MD5");
//        req.setDeviceValue("xxx");
//        req.setDeviceType("IMEI");
        TbkDgMaterialOptionalResponse rsp = null;
        try {
            rsp = client.execute(req);
        } catch (ApiException e) {
            log.error(rsp.getBody());
            e.printStackTrace();
        }
//        System.out.println(rsp.getBody());
        if (!rsp.isSuccess()) {
            log.error(rsp.getBody());
        }
        return rsp;
    }

    /**
     * 淘宝商品详情抓取
     *
     * @param itemId
     */
//    public TbkItemInfoGetResponse tbkItemInfoGet(List<Long> itemId) {
//        if (itemId == null || itemId.size() == 0) {
//            return null;
//        }
//        TaobaoClient client = getTaobaoClient();
//        TbkItemInfoGetRequest req = new TbkItemInfoGetRequest();
////       String str= String.join(",",itemId);
//        StringBuilder sb = new StringBuilder();
//        for (Long l : itemId) {
//            if (sb.length() > 0) {
//                sb.append(",");
//            }
//            sb.append(l);
//        }
//        req.setNumIids(sb.toString());
//        req.setPlatform(2L);
////        req.setIp("11.22.33.43");
//        TbkItemInfoGetResponse rsp = null;
//        try {
//            rsp = client.execute(req);
//        } catch (ApiException e) {
//            log.error(rsp.getBody());
//            e.printStackTrace();
//        }
//        System.out.println(rsp.getBody());
//        if (!rsp.isSuccess()) {
//            log.error(rsp.getBody());
//        }
//        return rsp;
//    }

    /**
     * 淘宝客链接解析
     */
    public void TbkItemClickExtract() {
//        TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
//        TbkItemClickExtractRequest req = new TbkItemClickExtractRequest();
//        req.setClickUrl("https://s.click.taobao.com/***");
//        TbkItemClickExtractResponse rsp = client.execute(req);
//        System.out.println(rsp.getBody());
    }

    /**
     * 商品链接转换
     */
    public void TbkItemConvert() {
//        TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
//        TbkItemConvertRequest req = new TbkItemConvertRequest();
//        req.setFields("num_iid,click_url");
//        req.setNumIids("123,456");
//        req.setAdzoneId(123L);
//        req.setPlatformName(123L);
//        req.setUnid("demo");
//        req.setDx("1");
//        TbkItemConvertResponse rsp = client.execute(req);
//        System.out.println(rsp.getBody());
    }

    /**
     * 生成淘口令
     */
    public TbkTpwdCreateResponse TbkTpwdCreate(String text,String url) {
        TaobaoClient client = getTaobaoClient();
        TbkTpwdCreateRequest req = new TbkTpwdCreateRequest();
//        req.setUserId("123");
        req.setText(text);
        req.setUrl(url);
//        req.setLogo("https://uland.taobao.com/");
//        req.setExt("{}");
        TbkTpwdCreateResponse rsp = null;
        try {
            rsp = client.execute(req);
        } catch (ApiException e) {
            e.printStackTrace();
        }
        return rsp;
    }

    /**
     * 好券清单
     */
//    public TbkDgItemCouponGetResponse couponGet(String keyWord, long pageSize, long pageIndex) {
//        long adzoneId = taobaoConfig.getTkbAdzoneId();
//        TaobaoClient client = getTaobaoClient();
//        TbkDgItemCouponGetRequest req = new TbkDgItemCouponGetRequest();
//        req.setAdzoneId(adzoneId);
//        req.setPlatform(2L);
//        req.setPageSize(pageSize);
//        req.setQ(keyWord);
//        req.setPageNo(pageIndex);
//        TbkDgItemCouponGetResponse rsp = null;
//        try {
//            rsp = client.execute(req);
//        } catch (ApiException e) {
//            e.printStackTrace();
//        }
//        return rsp;
//    }
}
