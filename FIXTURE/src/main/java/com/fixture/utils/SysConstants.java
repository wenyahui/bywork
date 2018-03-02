/*
 * 文件名：SysConstants.java
 * 版权：Copyright by www.taohuakeji.com
 * 描述：
 * 修改人：ying
 * 修改时间：2017-3-24
 */

package com.fixture.utils;

/**系统常量
 * @author ying
 * @version 2017-3-24
 * @see SysConstants
 * @since
 */

public class SysConstants
{
    public static String AUTHORIZATION ="Token"; //客户端定义标识头部的Token
    
    public static String MD5_SALT="taohua"; //MD5加盐
    
    public static String USER_ACTIVITY_INFO="USER_ACTIVITY";  //Session标识
    
    /** 验证码 */
    public static final String VERICODE_KEY =  "VERICODE_KEY";
    
    
    /**
    * 菜单类型
    */
   public enum MenuType {
       /**
        * 目录
        */
       CATALOG(0),
       /**
        * 菜单
        */
       MENU(1),
       /**
        * 按钮
        */
       BUTTON(2);

       private int value;

       private MenuType(int value) {
           this.value = value;
       }

       public int getValue() {
           return value;
       }
   }
   
   /**
    * 定时任务状态
    * 
    */
   public enum ScheduleStatus {
       /**
        * 正常
        */
       NORMAL(0),
       /**
        * 暂停
        */
       PAUSE(1);

       private int value;

       private ScheduleStatus(int value) {
           this.value = value;
       }
       
       public int getValue() {
           return value;
       }
   }
   /**
    * 定时任务状态
    * 
    */
   public enum ENABLE {
	   /**
	    * 禁用
	    */
	   UNENABLE(0),
	   /**
	    * 可用
	    */
	   ENABLE(1);
	   
	   private int value;
	   
	   private ENABLE(int value) {
		   this.value = value;
	   }
	   
	   public int getValue() {
		   return value;
	   }
   }
}
