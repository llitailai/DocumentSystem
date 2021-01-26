package com.nxftl.doc.common.interceptor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.nxftl.doc.common.annotation.RequiredToken;
import com.nxftl.doc.common.api.ApiCode;
import com.nxftl.doc.common.api.ApiResult;
import com.nxftl.doc.common.util.Token;
import com.nxftl.doc.sys.user.service.ISysUserService;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;

/**
 * @author darkltl
 * @email darkltl@163.com
 * @date 2021/1/26 16:56
 * @discription
 */
public class UserLoginInterceptor implements HandlerInterceptor {
    @Resource
    ISysUserService sysUserService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String token = request.getHeader("token");
        //如果不是映射到方法直接通过
        if(!(handler instanceof HandlerMethod)){
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod)handler;
        Method method = handlerMethod.getMethod();
        if(method.isAnnotationPresent(RequiredToken.class)){
            DecodedJWT tokenInfo = Token.getTokenInfo(token);
            if(token != null && tokenInfo!=null){
                String password = sysUserService.getPasswordById(Token.getUserId(tokenInfo));
                //验证是否修改过密码
                if(!Token.isUpdatedPassword(tokenInfo,password)){
                    //如果需要重新创建一个token,则通知客户端保存新的token,并且将新的token返回
                    if(Token.needCreate(tokenInfo)){
                        JSONObject tokenJson = new JSONObject();
                        tokenJson.put("token",Token.createToken(Token.getUserId(tokenInfo),password));
                        result(response,new ApiResult<>().success(ApiCode.SUCCESS,tokenJson));
                        return false;
                    }else{
                        return true;
                    }
                }

                //验证未通过


            }
        }
        return false;
    }

    private void result(HttpServletResponse response,ApiResult result){
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
        }
    }

}
