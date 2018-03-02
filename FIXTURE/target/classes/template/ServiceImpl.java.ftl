package ${package}.${pathName}.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.taohua.base.DataResult;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taohua.utils.Query;

import ${package}.${pathName}.dao.${className}Mapper;
import ${package}.${pathName}.po.${className};
import ${package}.${pathName}.service.I${className}Service;



@Service
public class ${className}ServiceImpl implements I${className}Service {

	@Autowired
	private ${className}Mapper ${classname}Dao;
	
	@Override
	public ${className} queryObject(${pk.attrType} ${pk.attrname}){
		return ${classname}Dao.queryObject(${pk.attrname});
	}
	
	@Override
	public DataResult queryList(Query query){
	
		//分页处理
        PageHelper.startPage(query.getPage(),query.getLimit());
        Map<String,String> map = new HashMap<String,String>();
        map.put("sidx", query.getSidx());
        map.put("order", query.getOrder());
        List<${className}> list = ${classname}Dao.queryList(map);
        if(list != null &&list.size()>0)
        {
          //获取分页信息
            PageInfo<${className}> info = new PageInfo<>(list);
            int total = (int)info.getTotal();
            //封装分页信息
            List<${className}> row = info.getList();
            return new DataResult(row,total,query.getLimit(),query.getPage());
        }
		return null;
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return ${classname}Dao.queryTotal(map);
	}
	
	@Override
	public void save(${className} ${classname}){
		${classname}Dao.save(${classname});
	}
	
	@Override
	public void update(${className} ${classname}){
		${classname}Dao.update(${classname});
	}
	
	@Override
	public void delete(${pk.attrType} ${pk.attrname}){
		${classname}Dao.delete(${pk.attrname});
	}
	
	@Override
	public void deleteBatch(${pk.attrType}[] ${pk.attrname}s){
		${classname}Dao.deleteBatch(${pk.attrname}s);
	}
	
}
