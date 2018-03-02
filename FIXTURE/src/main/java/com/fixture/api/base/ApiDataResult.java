/*
 * 文件名：ApiDataResult.java
 * 版权：Copyright by www.taohuakeji.com
 * 描述：
 * 修改人：ying
 * 修改时间：2017-3-24
 */

package com.fixture.api.base;

import java.util.Map;

/**
 * api接口返回实体类
 * @author ying
 * @version 2017-3-24
 * @see ApiDataResult
 * @since
 */

public class ApiDataResult
{
    /**
     * 结果代码 >0 返回正确，retMsg为空，非正整数时，返回错误，
     * retMsg不为空 , 建议默认使用100,此处不做强制化规定
     */
    private int retCode;
    //错误信息
    private String retMsg;
    //返回结果集
    private Map<Object,Object> dataMap;
    
    /**
     * @return Returns the retCode.
     */
    public int getRetCode()
    {
        return retCode;
    }
    /**
     * @param retCode The retCode to set.
     */
    public void setRetCode(int retCode)
    {
        this.retCode = retCode;
    }
    /**
     * @return Returns the retMsg.
     */
    public String getRetMsg()
    {
        return retMsg;
    }
    /**
     * @param retMsg The retMsg to set.
     */
    public void setRetMsg(String retMsg)
    {
        this.retMsg = retMsg;
    }
    /**
     * @return Returns the dataMap.
     */
    public Map<Object, Object> getDataMap()
    {
        return dataMap;
    }
    /**
     * @param dataMap The dataMap to set.
     */
    public void setDataMap(Map<Object, Object> dataMap)
    {
        this.dataMap = dataMap;
    }
    
    
    
}
