/*
 * 文件名：CommonException.java
 * 版权：Copyright by www.taohuakeji.com
 * 描述： 通用异常处理类
 * 修改人：ying
 * 修改时间：2017-3-21
 */

package com.fixture.exception;

/**
 * 通用异常处理类
 * @author ying
 * @version 2017-3-21
 * @see CommonException
 * @since
 */

public class CommonException extends Exception
{

    private String exceptionCode;
    private String errorMsg;
    
    
    
    /**
     * @return Returns the exceptionCode.
     */
    public String getExceptionCode()
    {
        return exceptionCode;
    }



    /**
     * @param exceptionCode The exceptionCode to set.
     */
    public void setExceptionCode(String exceptionCode)
    {
        this.exceptionCode = exceptionCode;
    }

    /**
     * @return Returns the errorMsg.
     */
    public String getErrorMsg()
    {
        return errorMsg;
    }



    /**
     * @param errorMsg The errorMsg to set.
     */
    public void setErrorMsg(String errorMsg)
    {
        this.errorMsg = errorMsg;
    }



    public CommonException(String exceptionCode, String errorMsg)
    {
        super(errorMsg);
        this.exceptionCode = exceptionCode;
    }
    
    public CommonException(String errorMsg, Throwable t){
        super(errorMsg, t);
    }
    
    public CommonException(String errorMsg){
        super(errorMsg);
    }
    
    public CommonException(Throwable t){
        super(t);
    }

}
