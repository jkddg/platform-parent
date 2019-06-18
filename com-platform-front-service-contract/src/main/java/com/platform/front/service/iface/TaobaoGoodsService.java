package com.platform.front.service.iface;

import com.platform.front.service.modal.GoodsInfo;

import java.util.List;

/**
 * Created by Huangyonghao on 2019/6/18 11:00.
 */
public interface TaobaoGoodsService {
   List<GoodsInfo> findGoodsList();
}
