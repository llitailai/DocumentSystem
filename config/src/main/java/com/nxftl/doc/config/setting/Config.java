package com.nxftl.doc.config.setting;

/**
 * @author darkltl
 * @email darkltl@163.com
 * @date 2021/1/26 16:49
 * @discription
 */
public interface Config {

    /**
     * 密钥
     */
    String SECRET_KEY = "NXftlCOD881122";

    /**
     * 过期时间
     */
    Integer TIME_OUT_DAY = 30;

    /**
     * 重新生成所需天数
     */
    Integer NEED_CREATE_DAY = 7;

    String USER_ID = "user_id";

    String KEY = "key";
}
