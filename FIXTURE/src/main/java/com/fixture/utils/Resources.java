package com.fixture.utils;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import org.springframework.context.i18n.LocaleContextHolder;

/**
 * 加载配置
 * 
 */
public final class Resources {
    /** 第三方登录配置 */
    public static final ResourceBundle THIRDPARTY = ResourceBundle.getBundle("config/thirdParty");
    /** 国际化信息 */
    private static final Map<String, ResourceBundle> MESSAGES = new HashMap<String, ResourceBundle>();
    /**短信**/
    public static final ResourceBundle SMS = ResourceBundle.getBundle("config/sms");
    /**高德地图Key**/
    public static final ResourceBundle MapKey = ResourceBundle.getBundle("config/map");
    /**微信**/
    public static final ResourceBundle  WEIXINKEY = ResourceBundle.getBundle("config/weixin");
    /**友盟**/
    public static final ResourceBundle  UMENG = ResourceBundle.getBundle("config/umeng");
    /**支付**/
    public static final ResourceBundle  PAY = ResourceBundle.getBundle("config/pay");
    /**常用配置*/
    public static final ResourceBundle  COMMONKEY = ResourceBundle.getBundle("config/syscommom");
    /**环信*/
    public static final ResourceBundle  EASEMOB = ResourceBundle.getBundle("config/easemob");
    /**solr*/
    public static final ResourceBundle  SOLRKEY= ResourceBundle.getBundle("config/solr");
    
    /** 国际化信息 */
    public static String getMessage(String key, Object... params) {
        Locale locale = LocaleContextHolder.getLocale();
        ResourceBundle message = MESSAGES.get(locale.getLanguage());
        if (message == null) {
            synchronized (MESSAGES) {
                message = MESSAGES.get(locale.getLanguage());
                if (message == null) {
                    message = ResourceBundle.getBundle("i18n/messages", locale);
                    MESSAGES.put(locale.getLanguage(), message);
                }
            }
        }
        if (params != null && params.length > 0) {
            return String.format(message.getString(key), params);
        }
        return message.getString(key);
    }

    /** 清除国际化信息 */
    public static void flushMessage() {
        MESSAGES.clear();
    }
}
