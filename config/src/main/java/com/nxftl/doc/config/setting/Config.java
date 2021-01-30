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

    String ACCOUNT_NOT_NULL = "账号不许为空";

    String PASSWORD_NOT_NULL = "密码不许为空";

    String ACCOUNT_OR_PASSWORD_FAIL = "账号或密码错误";

    String HAVE_NULL_PARAM = "您有未填写的信息";

    String GET = "get";

    String LOG_OUT = "logOut";

    String USER_PASS = "userPass";

    String TOKEN = "token";
}
