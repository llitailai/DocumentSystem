package com.nxftl.doc.common.handler;

import com.nxftl.doc.common.annotation.DeveloperEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author darkltl
 * @email darkltl@163.com
 * @date 2021/1/21 16:52
 * @discription
 */

@Component
public class EmailHandlerInterceptor implements HandlerInterceptor {
    Logger logger = LoggerFactory.getLogger(TokenInterceptor.class);
    public static String EMAIL = "email";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        DeveloperEmail email = ((HandlerMethod)handler).getMethodAnnotation(DeveloperEmail.class);
        if(handler.getClass().isAssignableFrom(HandlerMethod.class)){
            if(email != null){
                request.setAttribute(EMAIL,email.email());
            }
            return true;
        }else{
            return false;
        }
    }
}
