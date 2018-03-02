/*
 * 文件名：ThreadLocalUtil.java
 * 版权：Copyright by www.taohuakeji.com
 * 描述：
 * 修改人：ying
 * 修改时间：2017-3-28
 */

package com.fixture.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ying
 * @version 2017-3-28
 * @see ThreadLocalUtil
 * @since
 */

public class ThreadLocalUtil
{
    private static ThreadLocal<Map<String, Object>> threadLocal = new ThreadLocal<Map<String, Object>>();
    private static final String BASEPATH_KEY = "basepath";

    /**
     *
     * @param basePath
     */
    public static void setBasePath(String basePath) {
        Map<String, Object> map = threadLocal.get();
        if (map == null) {
            map = new HashMap<String, Object>();
            threadLocal.set(map);
        }
        map.put(BASEPATH_KEY, basePath);
    }

    /**
     * @return
     */
    public static String getBasePath() {
        Map<String, Object> map = threadLocal.get();
        if (map == null) {
            return null;
        }
        Object o = map.get(BASEPATH_KEY);
        return o == null ? null : o.toString();
    }
}
