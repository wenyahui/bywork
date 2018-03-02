<#macro jspEl value>${r"${"}${value}}</#macro>
$(function () {
    $("#jqGrid").jqGrid({
        url: window.basePath+'/${pathName}/list',
        datatype: "json",
        colModel: [			
		  <#list columns as column>
				<#if column.columnName == pk.columnName>
				{ label: '${column.attrname}', name: '${column.attrname}', index: '${column.columnName}', width: 50, key: true }
				<#else>
				{ label: '${column.comments}', name: '${column.attrname}', index: '${column.columnName}', width: 80 }
				</#if>
				<#if column_has_next>
				,
				</#if>				
           </#list>
        ],
		viewrecords: true,
        height: 385,
        rowNum: 10,
		rowList : [10,30,50],
        rownumbers: true, 
        rownumWidth: 25, 
        autowidth:true,
        multiselect: true,
        pager: "#jqGridPager",
        jsonReader : {
            root: "data.page.list",
            page: "data.page.currPage",
            total: "data.page.totalPage",
            records: "data.page.totalCount"
        },
        prmNames : {
            page:"page", 
            rows:"limit", 
            order: "order"
        },
        gridComplete:function(){
        	//隐藏grid底部滚动条
        	$("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" }); 
        }
    });
});