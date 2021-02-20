package com.nxftl.doc.common.util.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.nxftl.doc.config.setting.Config;
import lombok.extern.slf4j.Slf4j;

import java.util.Calendar;
import java.util.Date;

/**
 * @author darkltl
 * @email darkltl@163.com
 * @date 2021/1/26 16:43
 * @discription
 */
@Slf4j
public class Token {


    /**
     * 生成token
     * @param userId
     * @param password md5加密后密码
     * @return
     */
    public static String createToken(Long userId,String password){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE,Config.TIME_OUT_DAY);
        return JWT.create()
                .withClaim(Config.USER_ID, userId)
                .withClaim(Config.KEY, password)
                .withExpiresAt(calendar.getTime())
                .sign(Algorithm.HMAC256(Config.SECRET_KEY));
    }


    /**
     * 获取token信息 如果token有误则返回null
     * @param token
     * @return
     */
    public static DecodedJWT getTokenInfo(String token){
        try {
            return JWT.require(Algorithm.HMAC256(Config.SECRET_KEY)).build().verify(token);
        }catch (Exception e){
            return null;
        }
    }

    /**
     * 获取用户ID
     * @param decodedJWT
     * @return
     */
    public static Long getUserId(DecodedJWT decodedJWT){
        return decodedJWT.getClaim(Config.USER_ID).asLong();
    }


    /**
     * 验证是否修改过密码
     * @param password 数据库密码
     * @param decodedJWT
     * @return
     */
    public static boolean isUpdatedPassword(String password,DecodedJWT decodedJWT){
        String oldPassword = decodedJWT.getClaim(Config.KEY).asString();
        return oldPassword.equals(password);
    }


    /**
     * 是否需要重新生成token （为了延续token时长）
     * @param decodedJWT
     * @return
     */
    public static boolean needCreateImpl(DecodedJWT decodedJWT,boolean invalid){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE,Config.TIME_OUT_DAY - Config.NEED_CREATE_DAY);
        if(decodedJWT.getExpiresAt().before(!invalid?calendar.getTime():invalid())){
            return true;
        }
        return false;
    }

    public static boolean needCreate(DecodedJWT decodedJWT){
        return Token.needCreateImpl(decodedJWT,false);
    }

    public static boolean tokenInValid(DecodedJWT decodedJWT){
        return Token.needCreateImpl(decodedJWT,true);
    }


    private static Date invalid(){
        Date date = new Date();
        date.setTime(System.currentTimeMillis()<<2);
        return date;
    }
}
