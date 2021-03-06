package com.enjoy.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//如果这里注解，就不需要添加FilterConfig配置类
//@Component
public class MyCorsFilter implements Filter {

    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpServletRequest=(HttpServletRequest)request;
        HttpServletResponse httpServletResponse=(HttpServletResponse)response;
        // 存在跨域
        if (!httpServletRequest.getHeader("Origin").isEmpty()){
            httpServletResponse.setHeader("Access-Control-Allow-Origin", "http://localhost:8080");
            httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET");
            httpServletResponse.setHeader("Access-Control-Max-Age", "3600");
            httpServletResponse.setHeader("Access-Control-Allow-Headers", "x-requested-with");
        }
        chain.doFilter(httpServletRequest,httpServletResponse);
    }

    @Override
    public void destroy() {

    }
}
