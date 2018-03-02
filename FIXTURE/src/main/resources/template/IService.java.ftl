package ${package}.${pathName}.service;

import ${package}.${pathName}.po.${className};
import com.taohua.base.DataResult;
import java.util.List;
import java.util.Map;
import com.taohua.utils.Query;

/**
 * ${comments}
 * 
 * @author ${author}
 * @email ${email}
 * @date ${datetime}
 */
public interface I${className}Service {
	
	${className} queryObject(${pk.attrType} ${pk.attrname});
	
	DataResult queryList(Query query);
	
	int queryTotal(Map<String, Object> map);
	
	void save(${className} ${classname});
	
	void update(${className} ${classname});
	
	void delete(${pk.attrType} ${pk.attrname});
	
	void deleteBatch(${pk.attrType}[] ${pk.attrname}s);
}
