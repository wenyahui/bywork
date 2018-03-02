/*
 * 文件名：ExceptionInterception.java
 * 版权：Copyright by www.taohuakeji.com
 * 描述：
 * 修改人：ying
 * 修改时间：2017-3-21
 */

package com.fixture.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.fixture.base.BaseResult;
import com.fixture.utils.StringUtils;
import com.fixture.utils.ThreadLocalUtil;
import com.fixture.utils.WebUtils;

/**
 * 异常信息统一拦截
 * @author ying
 * @version 2017-3-21
 * @see ExceptionInterception
 * @since
 */
@Component
public class ExceptionInterception implements HandlerExceptionResolver
{
    private static Logger logger = LoggerFactory.getLogger(ExceptionInterception.class);

   /**
    * 异常信息跳转
    */
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response,
                                         Object handler, Exception ex)
    {
        logger.info("异常信息请求!");
        response.setContentType("text/html;charset=utf-8");
        String basePath = ThreadLocalUtil.getBasePath();
        boolean ajax = StringUtils.isAjax(request);
        String rtnUrl=request.getRequestURI();
        String errorCd = "";
        if (ajax || (rtnUrl != null && rtnUrl.contains("/api/"))) {
            logger.error("异常统一处理,异常信息{}",ex.getMessage());
            BaseResult resut  = new BaseResult();
            resut.setResultStatus(false);
            if(ex != null && ex.getMessage() != null)
            {
                if(ex.getMessage().contains("Subject does not have permission"))
                {
                    resut.setMsg("您没有权限操作！");
                }
                else
                {
                    resut.setMsg(ex.getMessage());
                }
            }
            else
            {
                resut.setMsg("系统繁忙");
            }
            WebUtils.apiObjectToJsonUtil(resut, response);
            return new ModelAndView();
        }
        //当不是api时
        ModelAndView model = new ModelAndView("error");
        model.addObject("errorCd", errorCd);
        String errorMsg = ex.getMessage();
        model.addObject("errorMsg", errorMsg);
        model.addObject("msg", "访问异常，请联系管理员!");
        return model;
        
        
    }
    

}
