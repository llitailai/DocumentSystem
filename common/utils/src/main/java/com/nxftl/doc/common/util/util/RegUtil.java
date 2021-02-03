package com.nxftl.doc.common.util.util;

import com.nxftl.doc.common.util.api.ApiCode;
import com.nxftl.doc.config.setting.Config;
import com.nxftl.doc.config.setting.reg.Reg;

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
    public static final void isPassword(String password){

        if (StringUtils.isEmpty(password)) {
            throw new BaseException(ApiCode.PARAM_IS_NULL);
        }
        verifyPassword(password);
    }

    /**
     * 校验邮箱是否正确
     * @param email
     */
    public static final void isEmail(String email){
        if(StringUtils.isEmpty(email))
            throw new BaseException(ApiCode.PARAM_IS_NULL);
        verifyEmail(email);
    }

    private static void verifyPassword(String password) {
        if(password.length() <= 8 && password.length() >= 30){
            throw new BaseException(ApiCode.INVALID_PASSWORD);
        }
    }



    private static void verifyEmail(String email) {
        if(!email.matches(Reg.EMAIL.getRegValue())){
            throw new BaseException(ApiCode.INVALID_EMAIL);
        }
    }

}
