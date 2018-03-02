/*
 * 文件名：PermissionFilter.java
 * 版权：Copyright by www.taohuakeji.com
 * 描述：
 * 修改人：ying
 * 修改时间：2017-3-28
 */

package com.fixture.filter;

import java.io.PrintWriter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;

import com.alibaba.fastjson.JSON;
import com.fixture.base.BaseResult;
import com.fixture.utils.StringUtils;
import com.fixture.utils.ThreadLocalUtil;

/**
 * 权限校验
 * @author ying
 * @version 2017-3-28
 * @see PermissionFilter
 * @since
 */

public class PermissionFilter extends AccessControlFilter
{
    private static Logger logger = Logger.getLogger(PermissionFilter.class);

    private String noPermissionUrl;

    public String getNoPermissionUrl() {
        return noPermissionUrl;
    }

    public void setNoPermissionUrl(String noPermissionUrl) {
        this.noPermissionUrl = noPermissionUrl;
    }
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        String pers[] = (String[]) mappedValue;
        if (pers == null || pers.length <= 0) {
            return true;
        }
        Subject subject = SecurityUtils.getSubject();

        boolean permittedAll = true;

        for (String ps : pers) {
            String[] parray = ps.split("-");
            boolean isPermitted = false;
            for (String s : parray) {
                isPermitted = isPermitted || subject.isPermitted(s);
            }
            permittedAll = permittedAll && isPermitted;
        }
        if (permittedAll) {
            return true;
        }
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String basePath = ThreadLocalUtil.getBasePath();
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        boolean isAjax = StringUtils.isAjax(httpRequest);
        if (isAjax) {
            httpResponse.setCharacterEncoding("UTF-8");
            PrintWriter writer = httpResponse.getWriter();
            BaseResult baseResult = new BaseResult();
            baseResult.setResultStatus(false);
            baseResult.setMsg("您没有操作权限");
            writer.write(JSON.toJSONString(baseResult));
            return false;
        }
        httpResponse.sendRedirect(basePath + getNoPermissionUrl());
        return false;
    }
}
