package com.nxftl.doc.common.util;

import org.apache.commons.codec.binary.Hex;

import java.security.MessageDigest;
import java.util.Random;

/**
 * @author darkltl
 * @email darkltl@163.com
 * @date 2021/1/25 17:54
 * @discription MD5加盐加密
 */
public class MD5 {

    /**
     * @Description : 加盐MD5
     * @Param  [password] 用户密码
     * @return : java.lang.String
     * @author : darkltl
     * @email darkltl@163.com
     * @Date : 2020/12/17
     * @Time : 13:57
     */
    public static String generate(String password) {
        Random r = new Random();
        StringBuilder sb = new StringBuilder(16);
        sb.append(r.nextInt(99999999)).append(r.nextInt(99999999));
        int len = sb.length();
        if (len < 16) {
            for (int i = 0; i < 16 - len; i++) {
                sb.append("0");
            }
        }
        String salt = sb.toString();
        password = md5Hex(password + salt);
        char[] cs = new char[48];
        for (int i = 0; i < 48; i += 3) {
            cs[i] = password.charAt(i / 3 * 2);
            char c = salt.charAt(i / 3);
            cs[i + 1] = c;
            cs[i + 2] = password.charAt(i / 3 * 2 + 1);
        }
        return new String(cs);
    }


    /**
     * @Description : 校验加盐后是否和原文一致
     * @Param  [password, md5]
     * @return : boolean
     * @author : darkltl
     * @email darkltl@163.com
     * @Date : 2020/12/17
     * @Time : 13:56
     */
    public static boolean verify(String password, String md5) {
        char[] cs1 = new char[32];
        char[] cs2 = new char[16];
        for (int i = 0; i < 48; i += 3) {
            cs1[i / 3 * 2] = md5.charAt(i);
            cs1[i / 3 * 2 + 1] = md5.charAt(i + 2);
            cs2[i / 3] = md5.charAt(i + 1);
        }
        String salt = new String(cs2);
        return md5Hex(password + salt).equals(new String(cs1));
    }



    /**
     * @Description : 获取十六进制字符串形式的MD5摘要
     * @Param  [src]
     * @return : java.lang.String
     * @author : darkltl
     * @email darkltl@163.com
     * @Date : 2020/12/17
     * @Time : 13:56
     */
    private static String md5Hex(String src) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] bs = md5.digest(src.getBytes());
            return new String(new Hex().encode(bs));
        } catch (Exception e) {
            return null;
        }
    }

    public static void main(String[] args) {}

}
