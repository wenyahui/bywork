/*
 * 文件名：LogAop.java 版权：Copyright by www.taohuakeji.com 描述： aop切面类 修改人：ying 修改时间：2017-3-21
 */

package com.fixture.aop;


import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSON;
import com.fixture.annotation.ControllerLog;
import com.fixture.annotation.ServiceLog;


/**
 * aop切面类
 * 
 * @author ying
 * @version 2017-3-21
 * @see LogAop
 * @since
 */
@Aspect
@Component
public class LogAop
{

    private static final Logger logger = Logger.getLogger("service_error");

    /**
     * Description: controller 的切面层 Implement: controller 的切面层
     * 
     * @see
     */
    @Pointcut("@annotation(com.fixture.annotation.ControllerLog)")
    public void controllerAspect()
    {}

    /**
     * Description: service 的切面层 Implement: service 的切面层
     * 
     * @see
     */
    @Pointcut("@annotation(com.fixture.annotation.ServiceLog)")
    public void serviceAspect()
    {}

    /**
     * Description: 前置通知 用于拦截Controller层记录用户的操作 Implement: <br>
     * 
     * @param joinPoint
     *            切点
     * @see
     */
    @Before("controllerAspect()")
    public void doBefore(JoinPoint joinPoint)
    {
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        // 读取session中的值
        // User user = (User) session.getAttribute(WebConstants.CURRENT_USER);
        // 请求的IP
        String ip = request.getRemoteAddr();
        try
        {
            // Log log = SpringContextHolder.getBean("logxx");
            // log.setDescription(getControllerMethodDescription(joinPoint));
            // log.setMethod((joinPoint.getTarget().getClass().getName() + "." +
            // joinPoint.getSignature().getName() + "()"));
            /**
             * 自定义注解类，然后将相关数据插入到数据库
             */
        }
        catch (Exception e)
        {
            logger.error("异常信息:" + e.getMessage());
        }
    }

    /**
     * Description:异常通知 用于拦截service层记录异常日志 Implement: 异常通知 用于拦截service层记录异常日志
     * 
     * @param joinPoint
     *            切面点
     * @param e
     * @see
     */
    @AfterThrowing(pointcut = "serviceAspect()", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Throwable e)
    {
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        // 根据session获取信息

        String params = "";
        if (joinPoint.getArgs() != null && joinPoint.getArgs().length > 0)
        {
            for (int i = 0; i < joinPoint.getArgs().length; i++ )
            {
                params += JSON.toJSONString(joinPoint.getArgs()[i]) + ";";
            }
        }
        // 日志入库
        // Log log = SpringContextHolder.getBean("logxx");
        // log.setDescription(getServiceMthodDescription(joinPoint));
        // log.setExceptionCode(e.getClass().getName());
        // log.setType("1");
        // log.setExceptionDetail(e.getMessage());
        // log.setMethod((joinPoint.getTarget().getClass().getName() + "." +
        // joinPoint.getSignature().getName() + "()"));
        // log.setParams(params);

    }

    /**
     * Description: 获取注解中对方法的描述信息 用于service层注解 Implement: 获取注解中对方法的描述信息 用于service层注解
     * 
     * @param joinPoint
     *            切点
     * @return 方法描述
     * @throws Exception
     * @see
     */
    public static String getServiceMthodDescription(JoinPoint joinPoint)
        throws Exception
    {
        String targetName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();
        Class targetClass = Class.forName(targetName);
        Method[] methods = targetClass.getMethods();
        String description = "";
        for (Method method : methods)
        {
            if (method.getName().equals(methodName))
            {
                Class[] clazzs = method.getParameterTypes();
                if (clazzs.length == arguments.length)
                {
                    description = method.getAnnotation(ServiceLog.class).description();
                    break;
                }
            }
        }
        return description;
    }

    /**
     * Description: 获取注解中对方法的描述信息 用于Controller层注解 Implement: 获取注解中对方法的描述信息 用于Controller层注解
     * 
     * @param joinPoint
     *            切点
     * @return 方法描述
     * @throws Exception
     * @see
     */
    public static String getControllerMethodDescription(JoinPoint joinPoint)
        throws Exception
    {
        String targetName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();
        Class targetClass = Class.forName(targetName);
        Method[] methods = targetClass.getMethods();
        String description = "";
        for (Method method : methods)
        {
            if (method.getName().equals(methodName))
            {
                Class[] clazzs = method.getParameterTypes();
                if (clazzs.length == arguments.length)
                {
                    description = method.getAnnotation(ControllerLog.class).description();
                    break;
                }
            }
        }
        return description;
    }

}
