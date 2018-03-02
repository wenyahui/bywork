/*
 * 文件名：SystemControllerLog.java
 * 版权：Copyright by www.taohuakeji.com
 * 描述：自动定义注解 拦截Controller
 * 修改人：ying
 * 修改时间：2017-3-21
 */

package com.fixture.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 自动定义注解 拦截Controller
 * 
 * @author ying
 * @version 2017-3-21
 * @see ControllerLog
 * @since
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})    
@Retention(RetentionPolicy.RUNTIME)    
@Documented
public @interface ControllerLog {
    
    String description() default "";

}
