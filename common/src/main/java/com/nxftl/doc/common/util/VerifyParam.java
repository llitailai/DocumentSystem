package com.nxftl.doc.common.util;


import com.nxftl.doc.common.annotation.NotNull;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author darkltl
 * @email darkltl@163.com
 * @date 2021/1/22 11:54
 * @discription 该方法服务于校验参数是否为空
 */
public class VerifyParam {


    /**
     * 校验是否具有 @NotNull 注解 如果有则判断参数是否为空,如果为空则抛出异常
     * @param verifyClass
     * @throws Exception
     */
    public static void verifyParam(Class<?> verifyClass) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Field[] declaredFields = verifyClass.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            NotNull notNull = declaredField.getAnnotation(NotNull.class);
            //如果具有该注解,代表不允许该字段为空
            if(notNull != null ){
                Method method = verifyClass.getMethod("get" + getMethodName(declaredField.getName()));
                Object invoke = method.invoke(verifyClass);
                if(invoke==null){
                    throw new BaseException(notNull.value());
                }
            }
        }
    }


    private static String getMethodName(String filedName){
        String first = filedName.substring(0, 1);
        return filedName.replaceFirst(first,first.toUpperCase());
    }
}
