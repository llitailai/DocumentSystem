package com.nxftl.doc.common.util.filter;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.ThreadContext;
import com.alibaba.fastjson.JSON;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author darkltl
 * @email darkltl@163.com
 * @date 2021/1/22 10:49
 * @discription
 */
@Slf4j
public class RequestLogFilter implements Filter{
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        ThreadContext.put("URL", request.getRequestURL().toString());
        ThreadContext.put("HttpMethod", request.getMethod());
        ThreadContext.put("IPAddr", request.getRemoteAddr());
        ThreadContext.put("ContentType", request.getContentType());

        // 获取所有参数名
        Map<String, String> paramNames = new HashMap<>();
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String name = parameterNames.nextElement();
            paramNames.put(name, request.getParameter(name));
        }
        ThreadContext.put("ParamNames", CollectionUtils.isEmpty(paramNames) ? "{}" : JSON.toJSONString(paramNames));

        //TODO 需要时打开注释
        // 记录升级至2020.3 Idea版本出现的问题 lombok失效 解决方式 settings compiler选项 Vm options 添加-Djps.track.ap.dependencies=false
         log.info("URL: " + request.getRequestURL().toString());
         log.info("Http Method: " + request.getMethod());
         log.info("IP Addr: " + request.getRemoteAddr());
         log.info("Content Type: " + request.getContentType());
         log.info("ParamNames: " + (CollectionUtils.isEmpty(paramNames) ? "{}" : JSON.toJSONString(paramNames)));

        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {
        //请求结束后清除信息
        ThreadContext.clearAll();
    }
}
