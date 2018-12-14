package com.liaoin.dfbs;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.liaoin.dfbs.bean.DfbsAssetInfo;
import com.liaoin.dfbs.bean.DfbsGoods;
import com.liaoin.dfbs.bean.RequestResult;
import com.liaoin.dfbs.constant.DfbsConstant;
import com.liaoin.dfbs.utils.MD5Util;
import com.liaoin.dfbs.utils.OkhttpUtils;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;

import java.io.IOException;
import java.util.*;

@Slf4j
public class DfbsVmUtils {

    /**
     * 获取商品信息
     */
    public static List<DfbsGoods> getGoodsLists() throws IOException {
        String url = DfbsConstant.URL.getGoodsLists + "?limit=" + DfbsConstant.limit + "&cursor=" + DfbsConstant.cursor;
        String nonce_str = DfbsVmUtils.genNonceStr();
        SortedMap<Object,Object> map = new TreeMap<Object,Object>();
        map.put("limit", DfbsConstant.limit);
        map.put("cursor", DfbsConstant.cursor);
        map.put("clientId", DfbsConstant.clientId);
        map.put("nonce_str",nonce_str);
        String sign = MD5Util.createSign("UTF-8", map, DfbsConstant.key);

        HashMap<String, String> params = new HashMap<>();
        params.put("clientId", DfbsConstant.clientId);
        params.put("nonce_str", nonce_str);
        params.put("sign", sign);
        Response requestResult = OkhttpUtils.post(url, params);
        String result = requestResult.body().string();
        RequestResult<List<DfbsGoods>> requestResults = new Gson().fromJson(result,new TypeToken<RequestResult<List<DfbsGoods>>>(){}.getType());
        log.info("售货机商品同步信息：" + result);
        List<DfbsGoods> goodsList = requestResults.getList();
        return goodsList;
    }

    public static String genNonceStr() {
        Random random = new Random();
        return MD5Util.getMessageDigest(String.valueOf(random.nextInt(10000)).getBytes());
    }

    /**
     * 通知出货
     */
    public static void noticeShipment(String url,String sign) throws Exception{
        Map<String,String> params = new HashMap<String,String>();
        params.put("payStatus","0");
        params.put("sign",sign);
        Response response = OkhttpUtils.post(url,params);
        log.info("通知出货结果：" + response.body().string());
    }

    /**
     * 获取设备信息
     */
    public static List<DfbsAssetInfo> queryAssetInfo()throws Exception{
        String url = DfbsConstant.URL.getAssetInfo + "?limit=" + DfbsConstant.limit + "&cursor=" + DfbsConstant.cursor;
        String nonce_str = DfbsVmUtils.genNonceStr();
        SortedMap<Object,Object> map = new TreeMap<Object,Object>();
        map.put("limit", DfbsConstant.limit);
        map.put("cursor", DfbsConstant.cursor);
        map.put("clientId", DfbsConstant.clientId);
        map.put("nonce_str",nonce_str);
        String sign = MD5Util.createSign("UTF-8", map, DfbsConstant.key);

        HashMap<String, String> params = new HashMap<>();
        params.put("clientId", DfbsConstant.clientId);
        params.put("nonce_str", nonce_str);
//        map.put("assetId", assetId);
        params.put("sign", sign);
        Response requestResult = OkhttpUtils.post(url, params);
        String result = requestResult.body().string();
        RequestResult<List<DfbsAssetInfo>> requestResults = new Gson().fromJson(result,new TypeToken<RequestResult<List<DfbsAssetInfo>>>(){}.getType());
        log.info("获取售货机信息结果：" + result);
        return requestResults.getList();
    }
}
