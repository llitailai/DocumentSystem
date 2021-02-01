package com.nxftl.doc.common.util.util;

/**
 * @author darkltl
 * @email darkltl@163.com
 * @date 2021/2/1 17:29
 * @discription
 */
public class RegUtil {

    /**
     * 校验密码
     * @param password
     * @return 长度符合返回true，否则为false
     * @author lqyao
     * @since 2015-09-24
     */
    public static final boolean isPassword(String password){
        if (null != password && !password.trim().equals("")) {
            password = password.trim();
            if(password.length() >= 8 && password.length() <= 30){
                return true;
            }
        }
        return false;
    }


}
