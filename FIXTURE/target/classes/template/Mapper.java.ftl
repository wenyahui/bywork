package ${package}.${pathName}.dao;

import ${package}.${pathName}.po.${className};
import java.util.List;
import java.util.Map;


/**
 * ${comments}
 * 
 * @author ${author}
 * @email ${email}
 * @date ${datetime}
 */
public interface ${className}Mapper {

	${className} queryObject(${pk.attrType} ${pk.attrname});
	
	List<${className}> queryList(Map<String,String> map);
	
	int queryTotal(Map<String, Object> map);
	
	int save(${className} ${classname});
	
	int update(${className} ${classname});
	
	int delete(${pk.attrType} ${pk.attrname});
	
	int deleteBatch(${pk.attrType}[] ${pk.attrname}s);
}
