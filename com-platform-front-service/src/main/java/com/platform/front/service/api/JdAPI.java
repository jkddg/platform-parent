package com.platform.front.service.api;

import com.jd.open.api.sdk.DefaultJdClient;
import com.jd.open.api.sdk.JdClient;
import com.jd.open.api.sdk.JdException;
import com.platform.front.service.config.JdConfig;
import jd.union.open.goods.jingfen.query.request.JFGoodsReq;
import jd.union.open.goods.jingfen.query.request.UnionOpenGoodsJingfenQueryRequest;
import jd.union.open.goods.jingfen.query.response.UnionOpenGoodsJingfenQueryResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JdAPI {

    @Autowired
    JdConfig jdConfig;

    protected JdClient getClient() {
        JdClient client = new DefaultJdClient(jdConfig.getServerUrl(), jdConfig.getAccessToken(), jdConfig.getAppKey(), jdConfig.getAppSecret());
        return client;
    }

    /**
     * 京粉精选商品查询接口
     *
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
}
