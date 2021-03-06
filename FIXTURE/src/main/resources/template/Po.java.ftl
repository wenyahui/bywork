package ${package}.${pathName}.po;

import java.io.Serializable;
import java.util.*;


/**
 * ${comments}
 * 
 * @author ${author}
 * @email ${email}
 * @date ${datetime}
 */
public class ${className} implements Serializable {
	private static final long serialVersionUID = 1L;
	
   <#list columns as column  >
	//${column.comments}
	private ${column.attrType} ${column.attrname};
   </#list>

   <#list columns as column>
	/**
	 * 设置：${column.comments}
	 */
	public void set${column.attrName}(${column.attrType} ${column.attrname}) {
		this.${column.attrname} = ${column.attrname};
	}
	/**
	 * 获取：${column.comments}
	 */
	public ${column.attrType} get${column.attrName}() {
		return ${column.attrname};
	}
  </#list>
}
