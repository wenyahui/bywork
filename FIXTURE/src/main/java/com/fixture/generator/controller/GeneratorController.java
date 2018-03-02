package com.fixture.generator.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.fixture.base.BaseResult;
import com.fixture.base.DataResult;
import com.fixture.generator.service.IGeneratorService;
import com.fixture.utils.Query;

/**
 * 自动生成代码
 * @author ying
 *
 */
@Controller
@RequestMapping("/sys/generator")
public class GeneratorController {
	
	@Autowired
	private IGeneratorService sysGeneratorService;
	
	
	@RequestMapping("/index")
	public String index(Model model)
	{
	    return "system/generator/generator";
	}
	/**
	 * 列表
	 */
	@ResponseBody
	@RequestMapping("/list")
	public BaseResult list(@RequestParam Map<String, Object> params){
		//查询列表数据
		Query query = new Query(params);
		DataResult page = sysGeneratorService.queryList(query.getPage(),query.getLimit());
		BaseResult result = new BaseResult();
		Map<String,Object> mapList = new HashMap<String,Object>();
		mapList.put("page", page);
		result.setData(mapList);
		result.setResultStatus(true);
		return result;
	}
	
	/**
	 * 生成代码
	 */
	@RequestMapping("/code")
	public void code(String tables, HttpServletResponse response) throws IOException{
		String[] tableNames = new String[]{};
		tableNames = JSON.parseArray(tables).toArray(tableNames);
		
		byte[] data = sysGeneratorService.generatorCode(tableNames);
		
		response.reset();  
        response.setHeader("Content-Disposition", "attachment; filename=\"taohua.zip\"");  
        response.addHeader("Content-Length", "" + data.length);  
        response.setContentType("application/octet-stream; charset=UTF-8");  
  
        IOUtils.write(data, response.getOutputStream());  
	}

}
