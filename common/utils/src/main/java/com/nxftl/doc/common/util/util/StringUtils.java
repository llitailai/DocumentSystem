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


    /**
     * 判断方法名称是否没有多个单词组成
     * 比如
     *  email true , tel true , account true, password true
     *  emailValid false, accountValid false
     * @param name
     * @return
     */
    private static synchronized boolean verifyNotUpperCase(String name) {
        String newName = name.toLowerCase();
        byte[] bytes = name.getBytes();
        byte[] newNameBytes = newName.getBytes();
        for(int i=0,let=bytes.length;i<let;i++){
            if((int)bytes[i] != (int)newNameBytes[i])
                return false;
        }
        return true;
    }

    public static boolean isEmpty(Object obj){
        return !verifyIsNull(obj);
    }

    private static synchronized boolean isEmptyContainsNotNullStr(Object obj){
        if(obj == null) return true;
        if(obj instanceof String)
            if(StringUtils.isEmpty(obj) || "null".equals(obj))
                return true;
        return false;
    }

    public static boolean isEmptyNotNullStr(Object obj){
        return isEmptyContainsNotNullStr(obj);
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


    public static boolean isNotHaveUpperCase(String name){return verifyNotUpperCase(name);}
}
