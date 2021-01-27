package com.nxftl.doc.common.auth.interceptor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.nxftl.doc.common.auth.utils.Token;
import com.nxftl.doc.common.util.annotation.RequiredToken;
import com.nxftl.doc.common.util.api.ApiCode;
import com.nxftl.doc.common.util.api.ApiResult;
import com.nxftl.doc.common.util.util.StringUtils;
import com.nxftl.doc.sys.user.service.ISysUserService;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author darkltl
 * @email darkltl@163.com
 * @date 2021/1/26 16:56
 * @discription
 */
@Component
public class UserLoginInterceptor implements HandlerInterceptor {
    @Resource
    ISysUserService sysUserService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        //如果不是映射到方法直接通过
        if(!(handler instanceof HandlerMethod)){
            return true;
        }
        return isHaveAnnotationRequired(((HandlerMethod)handler).getMethod().isAnnotationPresent(RequiredToken.class),request.getHeader("token"),response);
    }

    /**
     * 是否拥有RequiredToken的注解,如果有则继续
     * @param isHaveAnnotationRequired
     * @param token
     * @param response
     * @return
     */
    private boolean isHaveAnnotationRequired(boolean isHaveAnnotationRequired,String token,HttpServletResponse response){
        return isHaveAnnotationRequired?tokenIsNull(token, Token.getTokenInfo(token),response):true;
    }

    /**
     * 判断token是否为null,如果不为null则继续
     * @param token
     * @param tokenInfo
     * @param response
     * @return
     */
    private boolean tokenIsNull(String token,DecodedJWT tokenInfo,HttpServletResponse response){
        return StringUtils.isNotEmpty(token) && StringUtils.isNotEmpty(tokenInfo)?isUserUpdatePassword(sysUserService.getPasswordById(Token.getUserId(tokenInfo)),tokenInfo,response):result(response,new ApiResult().fail(ApiCode.NOT_TOKEN),false);
    }

    /**
     * 判断用户密码是否更改,如果没有更改则继续,更改则token失效
     * @param password
     * @param tokenInfo
     * @param response
     * @return
     */
    private boolean isUserUpdatePassword(String password,DecodedJWT tokenInfo,HttpServletResponse response){
        return !Token.isUpdatedPassword(tokenInfo,password)?isNeedCreateNewToken(tokenInfo,response,password):result(response,new ApiResult().fail(ApiCode.NOT_TOKEN),false);
    }

    private boolean isNeedCreateNewToken(DecodedJWT tokenInfo,HttpServletResponse response,String password){
        JSONObject tokenJson = new JSONObject();
        tokenJson.put("token",Token.createToken(Token.getUserId(tokenInfo),password));
        return Token.needCreate(tokenInfo)?result(response,new ApiResult<>().success(ApiCode.SUCCESS,tokenJson),false):true;
    }


    private boolean result(HttpServletResponse response,ApiResult result,boolean resultBoolean){
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        response.setStatus(200);
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.append(JSON.toJSONString(result));
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(out!=null)
                out.close();
            return resultBoolean;
        }
    }

}
