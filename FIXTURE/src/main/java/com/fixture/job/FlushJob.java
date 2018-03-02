/*
 * 文件名：FlushJob.java
 * 版权：Copyright by www.taohuakeji.com
 * 描述：
 * 修改人：ying
 * 修改时间：2017-4-10
 */

package com.fixture.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author ying
 * @version 2017-4-10
 * @see FlushJob
 * @since
 */

@Component
public class FlushJob
{
    private Logger loger = LoggerFactory.getLogger(this.getClass());
    
    public void flushActivity()
    {
        loger.info("------执行任务1----------");
    }

}
