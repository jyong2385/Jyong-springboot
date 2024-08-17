package com.jyong.springboot.filter;

import com.jyong.springboot.Util.LogUtil;
import com.jyong.springboot.Util.UserContextUtil;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

/**
 * @Author jyong
 * @Date 2024/5/18 16:44
 * @desc
 */
@Component
public class TraceFilter extends OncePerRequestFilter {

    /**
     * @param request
     * @param response
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        LogUtil.info(this.getClass(),"trace 链路追踪过滤器开始 ------");
        String traceId = UUID.randomUUID().toString().replace("-", "");
        MDC.put("traceId", traceId); // 生成或从请求头中提取traceId
        try {
            UserContextUtil.setTraceId(traceId);
            filterChain.doFilter(request, response);
        } finally {
            LogUtil.info(this.getClass(),"trace 链路追踪过滤器销毁 ------");
            MDC.remove("traceId");
        }
    }
}
