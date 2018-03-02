/*
 * 文件名：ShiroTagFreeMarkerConfigurer.java
 * 版权：Copyright by www.taohuakeji.com
 * 描述：
 * 修改人：ying
 * 修改时间：2017-4-6
 */

package com.fixture.utils;

import java.io.IOException;

import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.jagregory.shiro.freemarker.ShiroTags;

import freemarker.template.TemplateException;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author ying
 * @version 2017-4-6
 * @see ShiroTagFreeMarkerConfigurer
 * @since
 */

public class ShiroTagFreeMarkerConfigurer  extends FreeMarkerConfigurer 
{
    /**
     * shiro 标签说明
     * 
     * 1.<@shiro:guest></@shiro:guest> 
     *   验证当前用户是否为“访客”，即未认证（包含未记住）的用户
     * 2.<@shiro:user> </@shiro:user>  
     *  认证通过或已记住的用户 shiro标签
     * 3.<@shiro:authenticated> </@shiro:authenticated> 
     *   已认证通过的用户。不包含已记住的用户，这是与user标签的区别所在
     * 4.<@shiro.notAuthenticated></@shiro.notAuthenticated>  
     *   未认证通过的用户。与authenticated标签相对
     * 5.<@shiro.principal property="name" />  
     *   输出当前用户信息，通常为登录帐号信息
     * 6.<@shiro.hasRole name=”admin”></@shiro.hasRole>  
     *   验证当前用户是否属于该角色 
     * 7.<@shiro:hasAnyRoles name="admin,user,operator"> </@shiro:hasAnyRoles>
     *   验证当前用户是否属于这些角色中的任何一个，角色之间逗号分隔
     * 8.<@shiro:hasPermission name="/order:*"></@shiro:hasPermission>
     *   验证当前用户是否拥有该权限
     * 9.<@shiro.hasRole name="admin"></@shiro.hasRole> 
     *   验证当前用户不属于该角色，与hasRole标签想反
     * 10.<@shiro.lacksPermission name="/order:*"></@shiro.lacksPermission> 
     *   验证当前用户不拥有某种权限
     */
    @Override
    public void afterPropertiesSet() throws IOException, TemplateException {
        super.afterPropertiesSet();
        this.getConfiguration().setSharedVariable("shiro", new ShiroTags());
    }
}
