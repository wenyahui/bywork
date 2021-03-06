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
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author Administrator
 * @version 2017年5月5日
 * @see CommonController
 * @since
 */
@Controller
@RequestMapping("/")
public class KaptchaController 
{
    public static final String CAPTCHA_IMAGE_FORMAT = "jpeg";

    //--kapcha验证码。
    private Properties props = new Properties();
    private Producer kaptchaProducer = null;
    private String sessionKeyValue = null;
    private String sessionKeyDateValue = null;

    public KaptchaController() {
        ImageIO.setUseCache(false);

        //设置宽和高。
        this.props.put(com.google.code.kaptcha.Constants.KAPTCHA_IMAGE_WIDTH, "200");
        this.props.put(com.google.code.kaptcha.Constants.KAPTCHA_IMAGE_HEIGHT, "60");
        //kaptcha.border：是否显示边框。
        this.props.put(com.google.code.kaptcha.Constants.KAPTCHA_BORDER, "no");
        //kaptcha.textproducer.font.color：字体颜色
        this.props.put(com.google.code.kaptcha.Constants.KAPTCHA_TEXTPRODUCER_FONT_COLOR, "black");
        //kaptcha.textproducer.char.space：字符间距
        this.props.put(com.google.code.kaptcha.Constants.KAPTCHA_TEXTPRODUCER_CHAR_SPACE, "5");
        //设置字体。
        this.props.put(com.google.code.kaptcha.Constants.KAPTCHA_TEXTPRODUCER_FONT_SIZE, "40");
        //this.props.put(Constants.KAPTCHA_NOISE_COLOR, "");
        //更多的属性设置可以在com.google.code.kaptcha.Constants类中找到。
        Config config1 = new Config(this.props);
        this.kaptchaProducer = config1.getProducerImpl();
        this.sessionKeyValue = config1.getSessionKey();
        this.sessionKeyDateValue = config1.getSessionDate();
    }
    
  
    /**
     * 
     * Description: <br>
     * Implement: <br>
     *
     * @param request
     * @param response
     * @return
     * @throws Exception 
     * @see
     */
    @RequestMapping("/vericode")  
    public void vericode(HttpServletRequest request, HttpServletResponse response) throws Exception {  
        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/" + CAPTCHA_IMAGE_FORMAT);

        String capText = this.kaptchaProducer.createText();
        BufferedImage bi = this.kaptchaProducer.createImage(capText);
        ServletOutputStream out = response.getOutputStream();
        try {
            request.getSession().setAttribute("VERICODE_KEY", capText);
        } catch (Exception e) {
        	e.printStackTrace();
        }
        ImageIO.write(bi, CAPTCHA_IMAGE_FORMAT, out);
        try {  
            out.flush();  
        } finally {  
            out.close();  
        }  
    }
    
    
}
