package com.liaoin.dfbs.constant;

public interface DfbsConstant {

    public String clientId = "2066180927321755";

    public String key = "xU3ybbB4TtLwVoHdhYgrFGGTREr3YdrY";

    public int limit = 100000;

    public int cursor = 0;

    public interface  URL{

        public String HTTP_URL = "http://www.dfbs-vm.com";

        public String getGoodsLists = HTTP_URL + "/osapi/router/goods";

        public String getAssetInfo = HTTP_URL + "/osapi/router/status";
    }

    public interface  Result{

        public String SUCCESS = "SUCCESS";

        public String FAIL  = "FAIL ";
    }
}
