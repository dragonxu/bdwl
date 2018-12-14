package com.liaoin.dfbs.test;

import com.liaoin.dfbs.DfbsVmUtils;
import com.liaoin.dfbs.bean.DfbsGoods;
import okhttp3.Response;

import java.io.IOException;
import java.util.List;

public class DfbsTest {

    public static void main(String[] args) throws Exception {
//        String url = "http://www.dfbs-vm.com/api/pay/vipCommon/payback/CQBD0001/CQBD0001-000007-153812486166714";
//        String sign = "";
//       DfbsVmUtils.noticeShipment(url,sign);
//        List<DfbsGoods> dfbsGoodsList = DfbsVmUtils.getGoodsLists();
//        System.out.println(dfbsGoodsList);
        DfbsVmUtils.queryAssetInfo();
    }
}
