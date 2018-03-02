/*
 * 文件名：SwaggerConfig.java
 * 版权：Copyright by www.taohuakeji.com
 * 描述：
 * 修改人：ying
 * 修改时间：2017-3-23
 */

package com.fixture.api.swagger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.mangofactory.swagger.configuration.SpringSwaggerConfig;
import com.mangofactory.swagger.models.dto.ApiInfo;
import com.mangofactory.swagger.plugin.EnableSwagger;
import com.mangofactory.swagger.plugin.SwaggerSpringMvcPlugin;

/**
 * swagger相关配置
 * @author ying
 * @version 2017-3-23
 * @see SwaggerConfig
 * @since
 */

 
@Configuration
@EnableWebMvc
@EnableSwagger 
public class SwaggerConfig extends WebMvcConfigurerAdapter 
{
    
    private SpringSwaggerConfig springSwaggerConfig;

    @Autowired
    public void setSpringSwaggerConfig(SpringSwaggerConfig springSwaggerConfig) {
        this.springSwaggerConfig = springSwaggerConfig;
    }

    /**
     * 链式编程 来定制API样式 后续会加上分组信息
     * 
     * @return
     */
    @Bean
    public SwaggerSpringMvcPlugin customImplementation() {
        return new SwaggerSpringMvcPlugin(this.springSwaggerConfig)
                .apiInfo(apiInfo()).includePatterns(".*api.*")
                .useDefaultResponseMessages(false)
                .apiVersion("0.1");

    }
    
    private ApiInfo apiInfo() {
        ApiInfo apiInfo = new ApiInfo("后台API接口平台",
                "提供详细的后台所有restful接口", "http://taohua.com",
                "", "", "http://taohua.com");
        return apiInfo;
    }

  
}
