package com.fixture.utils;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 查询参数
 */
public class Query extends LinkedHashMap<String, Object> {
	private static final long serialVersionUID = 1L;
	//当前页码
    private int page;
    //每页条数
    private int limit;
    
    private String sidx; //排序字段
    
    private String order; //升序/降序

    public Query(Map<String, Object> params){
        this.putAll(params);

        //分页参数
        this.page = Integer.parseInt(params.get("page").toString());
        this.limit = Integer.parseInt(params.get("limit").toString());
        this.sidx = params.get("sidx")!= null?params.get("sidx").toString():null;
        this.order= params.get("order")!= null?params.get("order").toString():null;
        this.put("offset", (page - 1) * limit);
        this.put("page", page);
        this.put("limit", limit);
        this.put("sidx", sidx);
        this.put("order", order);
    }


    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }


    /**
     * @return Returns the sidx.
     */
    public String getSidx()
    {
        return sidx;
    }


    /**
     * @param sidx The sidx to set.
     */
    public void setSidx(String sidx)
    {
        this.sidx = sidx;
    }


    /**
     * @return Returns the order.
     */
    public String getOrder()
    {
        return order;
    }


    /**
     * @param order The order to set.
     */
    public void setOrder(String order)
    {
        this.order = order;
    }
    
    
    
}
