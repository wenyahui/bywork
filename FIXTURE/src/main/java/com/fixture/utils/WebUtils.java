/*
 * 文件名：WebUtils.java
 * 版权：Copyright by www.taohuakeji.com
 * 描述：
 * 修改人：ying
 * 修改时间：2017-3-24
 */

package com.fixture.utils;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 常用方法封装
 * @author ying
 * @version 2017-3-24
 * @see WebUtils
 * @since
 */

public class WebUtils
{
    private static  Logger logger = Logger.getLogger("controller_info");
    /**
     * 
     * Description: 创建新的Token
     *
     * @param userName 登录名称
     * @return
     * @throws Exception 
     * @see
     */
    public static String createNewToken(String userName) throws Exception
    {
        String token="";
        Map<String, Object> payload = new HashMap<String, Object>();
        Date date = new Date();
        payload.put("username", userName);
        payload.put("iat", date.getTime());// 生成时间
        payload.put("ext", date.getTime() + 2000 * 60 * 60);// 过期时间2小时
        token = Jwt.createToken(payload);
        return token;
    }
    
    /**
     * 
     * Description: 将实体以JSON格式传送
     * Implement: <br>
     *
     * @param t  实体
     * @param response  响应
     * @see
     */
    public static void apiObjectToJsonUtil(Object t,HttpServletResponse response)
    {
        response.setContentType("application/json");//设置response的传输格式为json 
        response.setCharacterEncoding("UTF-8");
        ObjectMapper mapper  = new ObjectMapper();
        try { 
            String result ="{\"retCode\":\"-1\",\"retMsg\":\"JSON数据转换异常!\",\"dataMap\":\"null\"}";
            if(t != null && !"".equals(t))
            {
                     
                    result = mapper.writeValueAsString(t);
                    logger.info("api传输数据，出参:"+result);
                   
            }
            PrintWriter out = response.getWriter(); 
            out.write(result);//给页面上传输json对象 
            out.flush();
        } catch (IOException e) { 
            logger.error("传输数据转换异常,异常信息:"+e.getMessage());
        }  
        
    }
    /** 获取当前用户 */
   /* public static final XSchoolLogin getCurrentUser() {
        Subject currentUser = SecurityUtils.getSubject();
        if (null != currentUser) {
            try {
                Session session = currentUser.getSession();
                if (null != session) {
                    return (XSchoolLogin) session.getAttribute("CURRENT_USER");
                }
            } catch (InvalidSessionException e) {
                logger.error(e);
            }
        }
        return null;
    }*/
}
