/*
 * 文件名：AntiSqlInjectionFilter.java
 * 版权：Copyright by www.taohuakeji.com
 * 描述： sql注入过滤器
 * 修改人：ying
 * 修改时间：2017-3-21
 */

package com.fixture.filter;

import java.io.IOException;
import java.util.Iterator;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.util.UrlPathHelper;

import com.fixture.utils.ThreadLocalUtil;

/**
 * sql注入过滤器
 * @author ying
 * @version 2017-3-21
 * @see AntiSqlInjectionFilter
 * @since
 */

public class AntiSqlInjectionFilter implements Filter
{

    private static final Logger logger = Logger.getLogger("controller_info");
    

    @Override
    public void init(FilterConfig arg0) throws ServletException
    {
        System.out.println("Sql filter inited!");
        
    }
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException
    {
        HttpServletRequest req=(HttpServletRequest)request;
        HttpServletResponse res=(HttpServletResponse)response;
        String path = req.getContextPath(); 
        String httpsPath=path;
    
        UrlPathHelper helper = new UrlPathHelper();
        String url = helper.getOriginatingRequestUri(req);
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
        ThreadLocalUtil.setBasePath(basePath);
        if(!url.contains("/back")){
             //获得所有请求参数名         
            Iterator values = req.getParameterMap().values().iterator();
            String sql = ""; 
            StringBuffer sqllog = new StringBuffer();
            while(values.hasNext()){     
                //得到参数对应值            
                String[] value = (String[])values.next(); 
                for (int i = 0; i < value.length; i++) { 
                    sql = sql + value[i]; 
                    sqllog.append(value[i]);
                    sqllog.append("; ");
                } 
            } 
            //System.out.println("============================SQL"+sql);         
            //有sql关键字，跳转到error.html         
            if (sqlValidate(sql, url, sqllog)) { 
                    res.sendRedirect(httpsPath+"/xsserror?msg=sqlin");
                } else { 
                chain.doFilter(request,response); 
            } 
        }else{
            chain.doFilter(request,response); 
        }
        
    }
    
    @Override
    public void destroy()
    {
        System.out.println("Sql filter destroyed!");    
        
    }
    
    //效验     
    protected static boolean sqlValidate(String str, String url, StringBuffer sqllog) { 
        str = str.toLowerCase();
        //统一转为小写        
        String badStr = "'|exec|insert|select|delete|update|*|%|#|drop|master|truncate|declare"; //过滤掉的sql关键字，可以手动添加 
        String[] badStrs = badStr.split("\\|"); 
        for (int i = 0; i < badStrs.length; i++) { 
            if (str.indexOf(badStrs[i]) >= 0) { 
                logger.info("AntiSqlInjectionFilter拦截：" + "[" + url + "]" + sqllog);
                logger.info("AntiSqlInjectionFilter发现SQL注入问题。");
                return false; 
            } 
        } 
        return false; 
    } 

}
