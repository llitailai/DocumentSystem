package com.nxftl.doc.common.util.util;

import com.nxftl.doc.common.util.api.ApiCode;
import com.nxftl.doc.config.setting.reg.Reg;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author darkltl
 * @email darkltl@163.com
 * @date 2021/2/1 17:29
 * @discription
 *  所有校验方法具体实现必须用verify作为前缀开头
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
     * @param verifyValue 需检验参数
     */
    public static void invokeFuzzyInvokeMethod(String fuzzyMethodName,String verifyValue){
        fuzzyInvokeMethod(fuzzyMethodName,verifyValue);
    }



    private static Message verifyTel(String tel) {
        if(!tel.matches(Reg.PHONE.getRegValue())){
            return new Message().setApiCode(ApiCode.INVALID_TEL);
        }
        return null;
    }

    private static Message verifyPassword(String password) {
        if(password.length() < 8 || password.length() >= 30){
            return new Message().setApiCode(ApiCode.INVALID_PASSWORD);
        }
        return null;
    }



    private static Message verifyEmail(String email) {
        if(!email.matches(Reg.EMAIL.getRegValue()) || email.length()>64){
            return new Message().setApiCode(ApiCode.INVALID_EMAIL);
        }
        return null;
    }


    /**
     * 根据方法名获取命中方法
     * @param fuzzyMethodName
     * @param verifyValue
     */
    private static void fuzzyInvokeMethod(String fuzzyMethodName,String verifyValue){
        Class<RegUtil> regUtilClass = RegUtil.class;
        hitMethods(regUtilClass,fuzzyMethodName,verifyValue);
    }


    /**
     * 命中方法具体实现.根据方法名字符串去看是否包含模糊方法,如果包含则执行,没有则抛出异常
     * 这只是一阶段实现 二阶段会对命中方法进行优化
     * TODO FIRST IMPL
     * @param cls
     * @param fuzzyMethodName
     * @param verifyValue
     */
    private static void hitMethods(Class<RegUtil> cls, String fuzzyMethodName,String verifyValue) {
        for (Method method : cls.getDeclaredMethods()) {
            String selfMethodName = findMethod(method.getName());
            if(selfMethodName != null && selfMethodName.toLowerCase().contains(fuzzyMethodName.toLowerCase())){
                invoke(method,verifyValue,cls);
                return;
            }
        }
        throw new BaseException(ApiCode.NOT_METHOD);
    }

    private static String findMethod(String methodName) {
        return find(methodName);
    }

    private static String find(String methodName) {
        if(methodName.contains("verify")){
            return methodName;
        }
        return null;
    }



    private static String isNotObject(String methodName) {
        if(methodName == null || foreachObjectMethodJudgeThisMethodNameIsObjectMethod(methodName))
            return null;
        return methodName;
    }

    private static boolean foreachObjectMethodJudgeThisMethodNameIsObjectMethod(String methodName) {
        for (Method declaredMethod : Object.class.getDeclaredMethods()) {
            if(declaredMethod.getName().equals(methodName))
                return true;
        }
        return false;
    }

    private static void invoke(Method method,String verifyValue,Class<RegUtil> cls) {
        Message msg ;
        try {
            method.setAccessible(true);
            msg = (Message) method.invoke(cls,verifyValue);
            if(msg != null){
                throw new BaseException(msg.getApiCode());
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }


}
