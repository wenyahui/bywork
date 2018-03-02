package com.fixture.utils;


import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;

import com.fixture.exception.RRException;
import com.fixture.generator.po.ColumnEntity;
import com.fixture.generator.po.TableEntity;

import freemarker.cache.FileTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.template.Template;
import freemarker.template.TemplateException;


/**
 * 代码生成工具
 * @author ying
 *
 */
public class GenUtils {

	public static List<String> getTemplates(){
		List<String> templates = new ArrayList<String>();
		templates.add("template/Po.java.ftl");
		templates.add("template/Mapper.java.ftl");
		templates.add("template/Mapper.xml.ftl");
		templates.add("template/IService.java.ftl");
		templates.add("template/ServiceImpl.java.ftl");
		templates.add("template/Controller.java.ftl");
		templates.add("template/list.html.ftl");
		templates.add("template/list.js.ftl");
		templates.add("template/menu.sql.ftl");
		return templates;
	}
	
	/**
	 * 生成代码
	 * @throws IOException 
	 * @throws TemplateException 
	 */
	public static void generatorCode(Map<String, String> table,
			List<Map<String, String>> columns, ZipOutputStream zip) throws TemplateException, IOException{
		//配置信息
		Configuration config = getConfig();
		
		//表信息
		TableEntity tableEntity = new TableEntity();
		tableEntity.setTableName(table.get("tableName"));
		tableEntity.setComments(table.get("tableComment"));
		//表名转换成Java类名
		String className = tableToJava(tableEntity.getTableName(), config.getString("tablePrefix"));
		tableEntity.setClassName(className);
		tableEntity.setClassname(StringUtils.uncapitalize(className));
		
		//列信息
		List<ColumnEntity> columsList = new ArrayList<>();
		for(Map<String, String> column : columns){
			ColumnEntity columnEntity = new ColumnEntity();
			columnEntity.setColumnName(column.get("columnName"));
			columnEntity.setDataType(column.get("dataType"));
			columnEntity.setComments(column.get("columnComment"));
			columnEntity.setExtra(column.get("extra"));
			
			//列名转换成Java属性名
			String attrName = columnToJava(columnEntity.getColumnName());
			columnEntity.setAttrName(attrName);
			columnEntity.setAttrname(StringUtils.uncapitalize(attrName));
			
			//列的数据类型，转换成Java类型
			String attrType = config.getString(columnEntity.getDataType(), "unknowType");
			columnEntity.setAttrType(attrType);
			
			//是否主键
			if("PRI".equalsIgnoreCase(column.get("columnKey")) && tableEntity.getPk() == null){
				tableEntity.setPk(columnEntity);
			}
			
			columsList.add(columnEntity);
		}
		tableEntity.setColumns(columsList);
		
		//没主键，则第一个字段为主键
		if(tableEntity.getPk() == null){
			tableEntity.setPk(tableEntity.getColumns().get(0));
		}
		//封装模板数据
		Map<String, Object> map = new HashMap<>();
		map.put("tableName", tableEntity.getTableName());
		map.put("comments", tableEntity.getComments());
		map.put("pk", tableEntity.getPk());
		map.put("className", tableEntity.getClassName());
		map.put("classname", tableEntity.getClassname());
		map.put("pathName", tableEntity.getClassname().toLowerCase());
		map.put("columns", tableEntity.getColumns());
		map.put("package", config.getString("package"));
		map.put("author", config.getString("author"));
		map.put("email", config.getString("email"));
		map.put("datetime", DateUtils.format(new Date(), DateUtils.DATE_PATTERN.YYYY_MM_DD_HH_MM_SS));
        
        
        
        //获取模板列表
		List<String> templates = getTemplates();
		TemplateLoader templateLoader=null; 
		
		for(String template : templates){
			//渲染模板
			StringWriter sw = new StringWriter();
			freemarker.template.Configuration configuration  = new freemarker.template.Configuration();
	        configuration.setClassForTemplateLoading(GenUtils.class, "/");
			Template tpl = configuration.getTemplate(template, "UTF-8");
			tpl.process(map,sw);
			
			try {
				//添加到zip
				zip.putNextEntry(new ZipEntry(getFileName(template, tableEntity.getClassName(), config.getString("package"))));  
				IOUtils.write(sw.toString(), zip, "UTF-8");
				IOUtils.closeQuietly(sw);
				zip.closeEntry();
			} catch (IOException e) {
				throw new RRException("渲染模板失败，表名：" + tableEntity.getTableName(), e);
			}
		}
	}
	
	
	/**
	 * 列名转换成Java属性名
	 */
	public static String columnToJava(String columnName) {
		return WordUtils.capitalizeFully(columnName, new char[]{'_'}).replace("_", "");
	}
	
	/**
	 * 表名转换成Java类名
	 */
	public static String tableToJava(String tableName, String tablePrefix) {
		if(StringUtils.isNotBlank(tablePrefix)){
			tableName = tableName.replace(tablePrefix, "");
		}
		return columnToJava(tableName);
	}
	
	/**
	 * 获取配置信息
	 */
	public static Configuration getConfig(){
		try {
			return new PropertiesConfiguration("generator.properties");
		} catch (ConfigurationException e) {
			throw new RRException("获取配置文件失败，", e);
		}
	}
	
	/**
	 * 获取文件名
	 */
	public static String getFileName(String template, String className, String packageName){
		String packagePath = "main" + File.separator + "java" + File.separator;
		if(StringUtils.isNotBlank(packageName)){
			packagePath += packageName.replace(".", File.separator) + File.separator;
		}
		
		if(template.contains("Po.java.ftl")){
			return packagePath +File.separator +className.toLowerCase()+File.separator + "po" + File.separator + className + ".java";
		}
		
		if(template.contains("Mapper.java.ftl")){
			return packagePath +File.separator +className.toLowerCase()+File.separator + "dao" + File.separator + className + "Mapper.java";
		}
		
		if(template.contains("Mapper.xml.ftl")){
			return packagePath +File.separator +className.toLowerCase()+File.separator + "mapper" + File.separator + className + "Mapper.xml";
		}
		
		if(template.contains("IService.java.ftl")){
			return packagePath +File.separator +className.toLowerCase()+File.separator + "service" + File.separator + "I"+className + "Service.java";
		}
		
		if(template.contains("ServiceImpl.java.ftl")){
			return packagePath +File.separator +className.toLowerCase()+File.separator + "service" + File.separator + "impl" + File.separator + className + "ServiceImpl.java";
		}
		
		if(template.contains("Controller.java.ftl")){
			return packagePath +File.separator +className.toLowerCase()+File.separator + "controller" + File.separator + className + "Controller.java";
		}
		
		if(template.contains("list.html.ftl")){
			return "main" + File.separator + "webapp" + File.separator + "WEB-INF" + File.separator + "main"
					+ File.separator + "system" + File.separator + className.toLowerCase()+File.separator + className.toLowerCase()+"list.html";
		}
		
		if(template.contains("list.js.ftl")){
			return "main" + File.separator + "webapp" + File.separator+"main"+File.separator+"static"+File.separator+"system" + File.separator + className.toLowerCase() + "list.js";
		}

		if(template.contains("menu.sql.ftl")){
			return className.toLowerCase() + "_menu.sql";
		}
		
		return null;
	}
}
