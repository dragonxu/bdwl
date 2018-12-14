package com.liaoin.constant;

/**
 * Created by Administrator on 2016/12/17 0017.
 */
public interface Constant {

    /**
     * 分页条数
     */
    public interface PageConstant{
        int PAGE_COUNT = 10;
        int ADMIN_PAGE_COUNT = 10;
    }

    /**
     * 文件路径
     */
    public interface PATH{
        public static String FILE_UPLOAD_PATH = "/upload";
        //用户资料
        public static String USER_DATA_PATH = "Userdata";
    }
}
