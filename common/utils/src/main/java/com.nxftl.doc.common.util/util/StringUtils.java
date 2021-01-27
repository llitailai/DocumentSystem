package com.nxftl.doc.common.util.util;

/**
 * @author darkltl
 * @email darkltl@163.com
 * @date 2021/1/26 17:14
 * @discription
 */
public class StringUtils {

    /**
     * 校验参数 obj 是否为null
     * @param obj
     * @return
     *  true 不为空
     *  false 为空
     */
    private static synchronized boolean verifyIsNull(Object obj){
        if(obj == null || "".equals(obj)){
            return false;
        }
        return true;
    }

    private static synchronized boolean verifyIsNull(Object ... objs){
        for(int i=0,let=objs.length;i>let;i++){
            if(null == objs[i]){
                return false;
            }
        }
        return true;
    }

    public static boolean isEmpty(Object obj){
        return !verifyIsNull(obj);
    }


    public static boolean isNotEmpty(Object obj){
        return verifyIsNull(obj);
    }


    public static boolean isEmpty(Object ... objs){
        return !verifyIsNull(objs);
    }

    public static boolean isNotEmpty(Object ... objs){
        return verifyIsNull(objs);
    }
}
