/*
 * 文件名：StringUtils.java
 * 版权：Copyright by www.taohuakeji.com
 * 描述： 字符串工具类
 * 修改人：ying
 * 修改时间：2017-3-21
 */

package com.fixture.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

/**
 * 字符串工具类
 * @author ying
 * @version 2017-3-21
 * @see StringUtils
 * @since
 */

public class StringUtils
{

    public static final String STRING_EMPTIY = "";

    public static boolean isEmpty(String param) {
        if (param == null || param.equals(STRING_EMPTIY) || param.equals("null")) {
            return true;
        }
        return false;
    }

    public static String trim(String param) {
        if (!isEmpty(param)) {
            return param.trim();
        }
        return STRING_EMPTIY;
    }
    
    public static String getStringByMap(String key, Map map){
        if(key!=null && map!=null){
            return map.get(key)==null?"":map.get(key).toString();
        }
        return "";
    }
    
    public static String getStringByMap(String key, Map map,String defaultValue){
        String returnValue = "";
        if(key!=null && map!=null){
            returnValue = map.get(key)==null?defaultValue:map.get(key).toString();
            if(!StringUtils.isEmpty(returnValue)){
                returnValue = defaultValue;
            }
        }
        return returnValue;
    }
    
    
    public static int getIntByMap(String key, Map map){
        if(key!=null && map!=null){
            return map.get(key)==null?-1:Integer.parseInt(map.get(key).toString());
        }
        return -1;
    }
        
        
    
    //格式化字符串为保留四位小数
    public static String getDecimalFormat(String accuStr){
        DecimalFormat df = new DecimalFormat("#0.0000");
        double unit_net = 0d;
        if(!StringUtils.isEmpty(accuStr)&&!accuStr.equals("0")){
            unit_net = Double.parseDouble(accuStr.replaceFirst("^0*", ""));
            return df.format(unit_net);
        }else{
            return "0.0000";
        }
    }
    
    public static String upperCase(String string) {
        return string.toUpperCase();
    }
    
    /**
     * 金额单位处理
     * 有小数部分 显示“元”
     * 大于10000 显示“万元” 小于10000 显示 “元”
     */
    public static String doUnit(BigDecimal amount){
        String temp = amount.toString();
        String[] arg = temp.split("\\.");
        if(arg.length==2){
            return amount+"元";
        }
        if(amount.compareTo(new BigDecimal(10000))==-1){
            return amount+"元";
        }else{
            return amount.divide(new BigDecimal(10000),2,BigDecimal.ROUND_HALF_UP)+"万元";
        }
    }

    /**
     * 空字符处理：字符为空的时候 ,返回""。
     */
    public static String isNullToBlank(String str) {
        return str == null ? "" : str;
    }
    
    /**
     * 校验传入字符串是否整型
     * @param value
     * @return
     */
    public static boolean isValidInt(String value){
        Matcher mer = Pattern.compile("^[+-]?[0-9]+$").matcher(value);   
        return mer.find();   
    }
    
    /**
     * 过滤字符串中的html标签
     * @param str
     * @return
     */
    public static String replaceHtmlStr(String str){
        // 过滤文章内容中的html
        str = str.replaceAll("</?[^<]+>", "");
        // 去除字符串中的空格 回车 换行符 制表符 等
        str = str.replaceAll("\\s*|\t|\r|\n", "");
        // 去除空格
        str = str.replaceAll("&nbsp;", "");
        return str.trim();
    }
    /**
     * 去除字符串前面所有的0，比如 000000154-->154
     * @param str
     * @return
     */
    public static String subBeforZero(String str)
    {
        String res = "";
        if(!isEmpty(str))
        {
            for(int i=0; i<str.length();i++)
            {
                char c = str.charAt(i);
                if(c !='0')
                {
                    res = str.substring(i, str.length());
                    break;
                }
            }
        }
        return res;
    }
    
    /**
     * 
     * Description: 验证请求是否为ajax请求
     *
     * @param request
     * @return 
     * @see
     */
    public static boolean isAjax(HttpServletRequest request) {
        boolean ajax = "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
        String ajaxFlag = null == request.getParameter("ajax") ? "false" : request.getParameter("ajax");
        boolean isAjax = ajax || ajaxFlag.equalsIgnoreCase("true");
        return isAjax;
    }
    
    /**
     * 
     * Description: 将对象转换成字节流
     *
     * @param obj
     * @return
     * @throws IOException 
     * @see
     */
    public static byte[] getBytesFromObject(Object obj) throws IOException {
        byte[] bytes = null;
        ByteArrayOutputStream bo = null;
        ObjectOutputStream oo = null;
        try {
            bo = new ByteArrayOutputStream();
            oo = new ObjectOutputStream(bo);
            oo.writeObject(obj);
            bytes = bo.toByteArray();
        } finally {
            if(bo!=null) {
                bo.close();
            }
            if(oo!=null) {
                oo.close();
            }
        }
        return bytes;
    }
    
    /**
     *
     * Description: 将字节流转换成对象
     *
     * @param bytes
     * @return
     * @throws IOException
     * @throws ClassNotFoundException 
     * @see
     */
    public static Object getObjectFromByteArray(byte[] bytes) throws IOException, ClassNotFoundException {
        Object obj = null;
        ByteArrayInputStream bi = null;
        ObjectInputStream oi = null;
        try {
            if(bytes==null){
                return null;
            }
            bi = new ByteArrayInputStream(bytes);
            oi = new ObjectInputStream(bi);
            obj = oi.readObject();
        } finally {
            if(bi!=null) {
                bi.close();
            }
            if(oi!=null) {
                oi.close();
            }
        }
        return obj;
    }
    
    private static final String HEADER_X_FORWARDED_FOR ="X-FORWARDED-FOR";
    public static String getIpAddr(HttpServletRequest request) {
        String remoteAddr = request.getRemoteAddr(); //先获取ip
        String x;
        if ((x = request.getHeader(HEADER_X_FORWARDED_FOR)) != null) {//加入存在这个头，可以判断是有进过代理的
            remoteAddr = x;
            int idx = remoteAddr.indexOf(','); //获取第一个,的下标
            if (idx > -1) {
                remoteAddr = remoteAddr.substring(0, idx);//拿到第一个IP地址就是真实的IP地址
            }
        }
        return remoteAddr;
    }
    
    /**
     * 
     * Description: string转Int 出错返回默认值<br>
     * Implement: <br>
     *
     * @param str
     * @param defaultValue
     * @return 
     * @see
     */
    public static int getIntValueOfString(String str, int defaultValue) {
        try {
            return Integer.parseInt(str.trim());
        } catch (Exception e) {
            return defaultValue;
        }
    }

}
