package com.jyong.springboot.filter;

import com.jyong.springboot.Util.LogUtil;
import com.jyong.springboot.Util.UserContextUtil;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;
import java.util.UUID;

/**
 * @Author jyong
 * @Date 2024/5/18 16:44
 * @desc
 * 过滤器实现方式一：实现 javax.servlet。Filter
 */
@Component
public class TraceFilter implements Filter {

    private final String TRACE_ID = "traceId";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        LogUtil.info(this.getClass(),"trace 链路追踪过滤器开始 ------");
        String traceId = UUID.randomUUID().toString().replace("-", "");
        MDC.put(TRACE_ID, traceId);
        UserContextUtil.setTraceId(traceId);
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void destroy() {
        MDC.remove(TRACE_ID);
        LogUtil.info(this.getClass(),"trace 链路追踪过滤器销毁 ------");
    }
}
