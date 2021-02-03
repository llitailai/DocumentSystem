package com.nxftl.doc.common.util.util;

import com.nxftl.doc.common.util.api.ApiCode;
import com.nxftl.doc.config.setting.Config;
import com.nxftl.doc.config.setting.reg.Reg;
import lombok.Data;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

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
        verifyPassword(password);
    }

    /**
     * 校验邮箱是否正确
     * @param email
     */
    public static final void isEmail(String email){
        verifyEmail(email);
    }


    /**
     * 检查是否是电话号码
     * @param tel
     */
    public static final void isTel(String tel){
        verifyTel(tel);
    }

    /**
     * 模糊查找方法并执行
     * @param fuzzyMethodName 模糊方法名
     * @param verifyValue 检验方法
     */
    public static void invokeFuzzyInvokeMethod(String fuzzyMethodName,String verifyValue){
        fuzzyInvokeMethod(fuzzyMethodName,verifyValue);
    }



    private static void verifyTel(String tel) {
        if(tel.matches(Reg.PHONE.getRegValue())){
            throw new BaseException(ApiCode.INVALID_TEL);
        }
    }

    private static void verifyPassword(String password) {
        if(password.length() <= 8 && password.length() >= 30){
            throw new BaseException(ApiCode.INVALID_PASSWORD);
        }
    }



    private static void verifyEmail(String email) {
        if(!email.matches(Reg.EMAIL.getRegValue()) || email.length()>64){
            throw new BaseException(ApiCode.INVALID_EMAIL);
        }
    }


    private static void fuzzyInvokeMethod(String fuzzyMethodName,String verifyValue){
        Class<RegUtil> regUtilClass = RegUtil.class;
        hitMethods(regUtilClass.getMethods(),fuzzyMethodName,verifyValue);
    }



    private static void hitMethods(Method[] methods, String fuzzyMethodName,String verifyValue) {
        for (Method method : methods) {
            if(method.getName().toLowerCase().contains(fuzzyMethodName.toLowerCase())){
                invoke(method,verifyValue);
                break;
            }
        }
        throw new BaseException(ApiCode.NOT_METHOD);
    }

    private static void invoke(Method method,String verifyValue) {
        try {
            method.invoke(new RegUtil(),verifyValue);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Data
    private class HitRate{

        /**
         * 命中次数
         */
        private Integer hitCount;


        private Integer K;
    }


}
