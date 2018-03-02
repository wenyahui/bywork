package com.fixture.generator.service;

import java.util.List;
import java.util.Map;

import com.fixture.base.DataResult;

public interface IGeneratorService {
	
    DataResult queryList(int pageNum,int pageSize);

    int queryTotal(Map<String, Object> map);
    
    Map<String, String> queryTable(String tableName);
    
    List<Map<String, String>> queryColumns(String tableName);
    
    /**
     * 生成代码
     */
    byte[] generatorCode(String[] tableNames);

}
