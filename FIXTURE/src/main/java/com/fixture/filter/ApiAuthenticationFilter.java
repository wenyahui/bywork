/*
 * 文件名：ApiAuthenticationFilter.java
 * 版权：Copyright by www.taohuakeji.com
 * 描述：
 * 修改人：ying
 * 修改时间：2017-3-24
 */

package com.fixture.filter;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.shiro.web.filter.authc.AuthenticationFilter;

import com.fixture.api.base.ApiDataResult;
import com.fixture.utils.Jwt;
import com.fixture.utils.StringUtils;
import com.fixture.utils.SysConstants;
import com.fixture.utils.WebUtils;

/**
 * api token验证
 * @author ying
 * @version 2017-3-24
 * @see ApiAuthenticationFilter
 * @since
 */

public class ApiAuthenticationFilter  extends AuthenticationFilter
{
    protected Logger log = Logger.getLogger("controller_info");
    
    @Override
    protected boolean onAccessDenied(ServletRequest request,ServletResponse response) throws Exception {

        HttpServletRequest req = (HttpServletRequest) request;
        
        String requestUrl = req.getRequestURI();
        log.info("API用户进入校验！" + requestUrl);
        if(requestUrl.contains("/api/")&&!requestUrl.contains("/login"))
        {
            //获取请求头部的Token
            String token = req.getHeader(SysConstants.AUTHORIZATION); 
            if (token != null && !StringUtils.isEmpty(token)) {
                //验证Token的有效性
                Map<String, Object> resultMap = new HashMap<String, Object>();
                resultMap = Jwt.validToken(token);
                if ((boolean) resultMap.get("isSuccess")) {
                    return true;
                } 
            }
            ApiDataResult dataResult = new ApiDataResult();
            dataResult.setRetCode(-1);
            dataResult.setRetMsg("token认证失效!");
            HttpServletResponse resp = (HttpServletResponse) response;
            WebUtils.apiObjectToJsonUtil(dataResult, resp);
            return false;
        }
        return true;
    }
    
}
