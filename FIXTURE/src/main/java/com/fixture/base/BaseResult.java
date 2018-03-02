/*
 * 文件名：BaseResult.java
 * 版权：Copyright by www.taohuakeji.com
 * 描述：
 * 修改人：ying
 * 修改时间：2017-3-28
 */

package com.fixture.base;

import java.util.Map;

/**
 * 返回的结果集
 * @author ying
 * @version 2017-3-28
 * @see BaseResult
 * @since
 */

public class BaseResult
{
    public boolean resultStatus; //返回状态
    
    public String msg; //提示信息
    
    public String url; //请求路径
    
    public Object data; //返回数据
    

    /**
     * @return Returns the resultStatus.
     */
    public boolean isResultStatus()
    {
        return resultStatus;
    }

    /**
     * @param resultStatus The resultStatus to set.
     */
    public void setResultStatus(boolean resultStatus)
    {
        this.resultStatus = resultStatus;
    }

    /**
     * @return Returns the msg.
     */
    public String getMsg()
    {
        return msg;
    }

    /**
     * @param msg The msg to set.
     */
    public void setMsg(String msg)
    {
        this.msg = msg;
    }

    /**
     * @return Returns the data.
     */
    public Object getData()
    {
        return data;
    }

    /**
     * @param data The data to set.
     */
    public void setData(Object data)
    {
        this.data = data;
    }

    /**
     * @return Returns the url.
     */
    public String getUrl()
    {
        return url;
    }

    /**
     * @param url The url to set.
     */
    public void setUrl(String url)
    {
        this.url = url;
    }
    
    
    
    
}
