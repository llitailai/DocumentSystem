package com.nxftl.doc.common.util.util;



import com.nxftl.doc.common.util.annotation.NotNull;
import com.nxftl.doc.common.util.http.HttpStatus;
import com.nxftl.doc.config.setting.Config;

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
     *
     * bug exception : method invoke 方法传入的第一个对象为 当前对象 而非当前字节码对象
     * @param verifyClass
     * @throws Exception
     */
    public static void verifyParam(Class<?> verifyClass,Object obj) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {
        Field[] declaredFields = verifyClass.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            NotNull notNull = declaredField.getAnnotation(NotNull.class);
            //如果具有该注解,代表不允许该字段为空
            if(notNull != null ){
                Method method = verifyClass.getMethod(Config.GET + getMethodName(declaredField.getName()));
                Object invoke = method.invoke(obj);
                if(StringUtils.isEmpty(invoke)){
                    throw new BaseException(HttpStatus.ACCEPTED,notNull.value());
                }
            }
        }
    }

    public static void verifyParam(Object ... obj) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        for (Object o : obj) {
            verifyString(o);
            verifyParam(obj.getClass());
        }
    }

    /**
     * 这里目前有一个问题,String类并没有自定义注解NotNull 所以获取不到NotNull的值
     * @param obj
     */
    private static void verifyString(Object obj){
        if(StringUtils.isEmpty(obj))
            throw new BaseException(HttpStatus.ACCEPTED, Config.HAVE_NULL_PARAM);
    }
    private static String getMethodName(String filedName){
        String first = filedName.substring(0, 1);
        return filedName.replaceFirst(first,first.toUpperCase());
    }
}
