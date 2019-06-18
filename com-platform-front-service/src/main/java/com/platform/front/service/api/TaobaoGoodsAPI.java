package com.platform.front.service.api;

import com.taobao.api.ApiException;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.TbkDgMaterialOptionalRequest;
import com.taobao.api.request.TbkItemInfoGetRequest;
import com.taobao.api.response.TbkDgMaterialOptionalResponse;
import com.taobao.api.response.TbkItemInfoGetResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * Created by Huangyonghao on 2019/6/18 14:49.
 */
@Slf4j
@Component
public class TaobaoGoodsAPI extends AbstractTaobao {
    long adzoneId;
    /**
     * 淘宝商品列表抓取
     *
     * @param pageSize
     * @param pageIndex
     */
    public TbkDgMaterialOptionalResponse tbkMaterialOptional(boolean hasCoupon, String categorys, String keyWord, long pageSize, long pageIndex) {
//        TaobaoClient client = new DefaultTaobaoClient("http://gw.api.taobao.com/router/rest", "25823044", "09486e171c16cbf652e800393ad0bded");
        TaobaoClient client = getTaobaoClient();
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
//        req.setCat("21,50016348");
//        req.setCat("16,18");
//        req.setCat("35,50014812,50022517,50008165,122650005,");
//        req.setStartDsr(10L);
//        req.setEndTkRate(1234L);
//        req.setStartTkRate(1234L);
//        req.setEndPrice(10L);
//        req.setStartPrice(10L);
//        req.setIsOverseas(false);
//        req.setIsTmall(false);
//        req.setSort("tk_rate_des");
//        req.setItemloc("杭州");

//        req.setQ("女装");
//        req.setMaterialId(2836L);
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
    public TbkItemInfoGetResponse tbkItemInfoGet(List<Long> itemId) {
        if (itemId == null || itemId.size() == 0) {
            return null;
        }
        TaobaoClient client = getTaobaoClient();
        TbkItemInfoGetRequest req = new TbkItemInfoGetRequest();
//       String str= String.join(",",itemId);
        StringBuilder sb = new StringBuilder();
        for (Long l : itemId) {
            if (sb.length() > 0) {
                sb.append(",");
            }
            sb.append(l);
        }
        req.setNumIids(sb.toString());
        req.setPlatform(2L);
//        req.setIp("11.22.33.43");
        TbkItemInfoGetResponse rsp = null;
        try {
            rsp = client.execute(req);
        } catch (ApiException e) {
            log.error(rsp.getBody());
            e.printStackTrace();
        }
        System.out.println(rsp.getBody());
        if (!rsp.isSuccess()) {
            log.error(rsp.getBody());
        }
        return rsp;
    }
}
