/*
 * 文件名：CorsFilter.java
 * 版权：Copyright by www.taohuakeji.com
 * 描述： CORS过滤器 
 * 修改人：ying
 * 修改时间：2017-3-21
 */

package com.fixture.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

/**
 *  CorsFileter 功能描述：CORS过滤器 
 * @author ying
 * @version 2017-3-21
 * @see CorsFilter
 * @since
 */
@Component  
public class CorsFilter implements Filter
{

    @Override
    public void destroy()
    {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
        throws IOException, ServletException
    {
        HttpServletResponse response = (HttpServletResponse) servletResponse;  
        response.setHeader("Access-Control-Allow-Origin", "*");  
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");  
        response.setHeader("Access-Control-Max-Age", "3600");  
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with,Authorization");  
        response.setHeader("Access-Control-Allow-Credentials", "true");  
        
        filterChain.doFilter(servletRequest, servletResponse);  
        
    }

    @Override
    public void init(FilterConfig arg0)
        throws ServletException
    {
        
    }

}
