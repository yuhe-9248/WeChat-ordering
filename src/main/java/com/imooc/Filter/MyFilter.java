package com.imooc.Filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author yuhe
 * @date 2022/2/19 11:25
 */
@WebFilter(filterName = "MyFilter",urlPatterns = "/*")
public class MyFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        request.setCharacterEncoding("UTF-8");
        filterChain.doFilter(request,servletResponse);
    }

    @Override
    public void destroy() {

    }
}
