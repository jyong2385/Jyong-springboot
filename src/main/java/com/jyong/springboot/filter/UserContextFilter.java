package com.jyong.springboot.filter;


import com.jyong.springboot.Util.LogUtil;
import com.jyong.springboot.Util.UserContextUtil;
import com.jyong.springboot.dao.model.User;
import com.jyong.springboot.enums.UserInfo;
import com.jyong.springboot.service.UserService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Jyong
 * @date 2024/8/17 10:23
 * @description
 *
 * 过滤器实现方式二：继承：org.springframework.web.filter.OncePerRequestFilter
 */
@Component
public class UserContextFilter extends OncePerRequestFilter {

    private String[] IGNORE_PATHS = {"/user/register.json"};

    @Resource
    private UserService userService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        LogUtil.info(this.getClass(),"用户上下文初始化 过滤器开始 ------");
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            boolean foundUsernameCookie = false;
            for (Cookie cookie : cookies) {
                if ("userId".equals(cookie.getName())) {
                    String userId = cookie.getValue();
                    User user = userService.selectById(Long.parseLong(userId));
                    if (user != null) {
                        foundUsernameCookie = true;
                        UserContextUtil.setUserInfo(user.getId(),user.getName());
                    }
                    break;
                }
            }

            if (!foundUsernameCookie) {
                // No username cookie found, redirect or return an error response
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "cookie中无用户信息");
                return;
            }
        } else {
            // No cookies found, redirect or return an error response
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "用户没有登陆");
            return;
        }

        try {
            filterChain.doFilter(request, response);
        } finally {
            UserContextUtil.clear();
            LogUtil.info(this.getClass(),"用户上下文初始化 过滤器销毁 ------");
        }

    }
}
