/*
 * 文件名：CommonController.java
 * 版权：Copyright by www.taohuakeji.com
 * 描述：
 * 修改人：Administrator
 * 修改时间：2017年5月5日
 */

package com.fixture.controller;

import java.awt.image.BufferedImage;
import java.util.Properties;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.util.Config;

/**
 * 
* @ClassName: IndexController 
* @Description: TODO
* @author wyh
* @date 2018年3月1日 下午5:09:48 
*
 */
@Controller
@RequestMapping("/")
public class IndexController{
	
	@RequestMapping("")
	public String login(){
		return "login";
	}
	@RequestMapping("/index")
	public String index(){
		return "index";
	}
}
