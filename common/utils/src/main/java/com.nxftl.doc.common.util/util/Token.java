package com.nxftl.doc.common.util.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.nxftl.doc.config.setting.Config;
import org.springframework.util.DigestUtils;

import java.util.Calendar;
import java.util.Date;

/**
 * @author darkltl
 * @email darkltl@163.com
 * @date 2021/1/26 16:43
 * @discription
 */
public class Token {


    /**
     * 生成token
     * @param user
     * @return
     */
    public static String createToken(Long userId,String password){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE,Config.TIME_OUT_DAY);
        String token = JWT.create()
                .withClaim(Config.USER_ID, userId)
                .withClaim(Config.KEY, DigestUtils.md5DigestAsHex(password.getBytes()))
                .withExpiresAt(calendar.getTime())
                .sign(Algorithm.HMAC256(Config.SECRET_KEY));
        return token;
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
    public static Integer getUserId(DecodedJWT decodedJWT){
        return decodedJWT.getClaim(Config.USER_ID).asInt();
    }


    /**
     * 验证是否修改过密码
     * @param decodedJWT
     * @param password 密码
     * @return
     */
    public static boolean isUpdatedPassword(DecodedJWT decodedJWT,String password){
        String oldPwd = decodedJWT.getClaim(Config.KEY).asString();
        String newPwd = DigestUtils.md5DigestAsHex(password.getBytes());
        return oldPwd.equals(newPwd)?false:true;
    }


    /**
     * 是否需要重新生成token （为了延续token时长）
     * @param decodedJWT
     * @return
     */
    public static boolean needCreate(DecodedJWT decodedJWT){
        Date timeoutDate = decodedJWT.getExpiresAt();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE,Config.TIME_OUT_DAY - Config.NEED_CREATE_DAY);
        if(timeoutDate.before(calendar.getTime())){
            return true;
        }
        return false;
    }

}
