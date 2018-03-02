package com.fixture.generator.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fixture.base.DataResult;
import com.fixture.generator.dao.GeneratorMapper;
import com.fixture.generator.service.IGeneratorService;
import com.fixture.utils.GenUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import freemarker.template.TemplateException;


@Service
public class GeneratorServiceImpl implements IGeneratorService {
	
	@Autowired
	private GeneratorMapper sysGeneratorDao;

	@Override
	public DataResult queryList(int pageNum, int pageSize) {
	    //分页处理
        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> list = sysGeneratorDao.queryList(null);
        if(list != null &&list.size()>0)
        {
          //获取分页信息
            PageInfo<Map<String, Object>> info = new PageInfo<>(list);
            int total = (int)info.getTotal();
            //封装分页信息
            List<Map<String, Object>> row = info.getList();
            return new DataResult(row,total,pageSize,pageNum);
        }
		return null;
	}

	@Override
	public int queryTotal(Map<String, Object> map) {
		return sysGeneratorDao.queryTotal(map);
	}

	@Override
	public Map<String, String> queryTable(String tableName) {
		return sysGeneratorDao.queryTable(tableName);
	}

	@Override
	public List<Map<String, String>> queryColumns(String tableName) {
		return sysGeneratorDao.queryColumns(tableName);
	}

	@Override
	public byte[] generatorCode(String[] tableNames) {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		ZipOutputStream zip = new ZipOutputStream(outputStream);
		
		for(String tableName : tableNames){
			//查询表信息
			Map<String, String> table = queryTable(tableName);
			//查询列信息
			List<Map<String, String>> columns = queryColumns(tableName);
			//生成代码
			try {
				GenUtils.generatorCode(table, columns, zip);
			} catch (TemplateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		IOUtils.closeQuietly(zip);
		return outputStream.toByteArray();
	}

}
