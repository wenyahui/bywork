/*
 * 文件名：GeneratorTool.java
 * 版权：Copyright by www.taohuakeji.com
 * 描述：
 * 修改人：ying
 * 修改时间：2017-3-28
 */

package com.fixture.generator;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.api.ProgressCallback;
import org.mybatis.generator.api.VerboseProgressCallback;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

/**
 * mybatis 自动生成工具
 * @author ying
 * @version 2017-3-28
 * @see GeneratorTool
 * @since
 */

public class GeneratorTool
{
    public static void main(String[] args) {
        shell();
    }
    
    private static void shell() {
        List<String> warnings = new ArrayList<String>();
        try {
            String configFilePath = GeneratorTool.class.getClassLoader().getResource("generatorConfig.xml").getPath();
                    
            System.out.println("加载配置文件:" + configFilePath);
            boolean overwrite = true;
            File configFile = new File(configFilePath);
            ConfigurationParser cp = new ConfigurationParser(warnings);
            Configuration config = cp.parseConfiguration(configFile);
            DefaultShellCallback callback = new DefaultShellCallback(overwrite);
            MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config,
                    callback, warnings);
            ProgressCallback progressCallback = new VerboseProgressCallback();
            // myBatisGenerator.generate(null);
            myBatisGenerator.generate(progressCallback);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        for (String warning : warnings) {
            System.out.println(warning);
        }
    }
}
