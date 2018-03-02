package ${package}.${pathName}.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Date;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import ${package}.${pathName}.po.${className};
import ${package}.${pathName}.service.I${className}Service;
import com.alibaba.fastjson.JSON;
import com.taohua.base.BaseResult;
import com.taohua.base.DataResult;
import com.taohua.utils.Query;


/**
 * ${comments}
 * 
 * @author ${author}
 * @email ${email}
 * @date ${datetime}
 */
@Controller
@RequestMapping("/${pathName}")
public class ${className}Controller {

	@Autowired
	private I${className}Service ${classname}Service;
	
	
	@RequestMapping("/index")
	public String index(Model model) throws Exception
	{
		Date date = new Date();
        long dateTime = date.getTime();
        model.addAttribute("dateTime", dateTime);
	    return "system/${pathName}/${pathName}list";
	}
	
	/**
	 * 列表
	 */
	@ResponseBody
	@RequestMapping("/list")
	@RequiresPermissions("${pathName}:list")
	public BaseResult list(@RequestParam Map<String, Object> params) throws Exception{
	
		//查询列表数据
		Query query = new Query(params);
		DataResult page = ${classname}Service.queryList(query);
		BaseResult result = new BaseResult();
		Map<String,Object> mapList = new HashMap<String,Object>();
		mapList.put("page", page);
		result.setData(mapList);
		result.setResultStatus(true);
		return result;
	}
	
	
	/**
	 * 信息
	 */
	@ResponseBody
	@RequestMapping("/info/{${pk.attrname}}")
	@RequiresPermissions("${pathName}:info")
	public BaseResult info(@PathVariable("${pk.attrname}") ${pk.attrType} ${pk.attrname}) throws Exception{
		BaseResult result = new BaseResult();
		${className} ${classname} = ${classname}Service.queryObject(${pk.attrname});
		Map<String,Object> mapList = new HashMap<String,Object>();
		mapList.put("${classname}", ${classname});
		result.setData(mapList);
		result.setResultStatus(true);
		return result;
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@RequestMapping("/save")
	@RequiresPermissions("${pathName}:save")
	public BaseResult save(@RequestBody ${className} ${classname}) throws Exception{
	    BaseResult result = new BaseResult();
		${classname}Service.save(${classname});
		result.setResultStatus(true);
		return result;
	}
	
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("${pathName}:update")
	public BaseResult update(@RequestBody ${className} ${classname}) throws Exception{
		 BaseResult result = new BaseResult();
		${classname}Service.update(${classname});
		result.setResultStatus(true);
		return result;
	}
	
	/**
	 * 删除
	 */
	@ResponseBody
	@RequestMapping("/delete")
	@RequiresPermissions("${pathName}:delete")
	public BaseResult delete(@RequestBody ${pk.attrType}[] ${pk.attrname}s) throws Exception{
		${classname}Service.deleteBatch(${pk.attrname}s);
		BaseResult result = new BaseResult();
		result.setResultStatus(true);
		return result;
	}
	
}
